package com.iqes.web.queue;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.QueueHistory;
import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import com.iqes.entity.co.CalculateTimeVO;
import com.iqes.entity.co.CheckTimeCo;
import com.iqes.entity.vo.WaitTimeModel;
import com.iqes.service.queue.QueueHistoryService;
import com.iqes.service.queue.QueueQueryService;
import com.iqes.service.queue.TableService;
import com.iqes.utils.TimeFormatTool;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * 顾客虚拟入队并确认入队
 *
 * @author jiangdongdong.tp
 *
 */
@Controller
@RequestMapping(value ="/queue")
public class QueueUpController {

    @Autowired
    private QueueQueryService queueQueryService;
    @Autowired
    private TableService tableService;
    @Autowired
    private QueueHistoryService queueHistoryService;


    /**
     * 返回直立机主页面
     * @return String
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String backQueueHome(){
        return "queue/queueHome";
    }
    /**
     * 返回直立机主页面
     * @return String
     */
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String backQueueSucess(){
        return "queue/queueSuccess";
    }
    /**
     * 传递并返回预约排队页面
     * @param queueId
     * @param model
     * @return String
     */
    @RequestMapping(value = "/lineup", method = RequestMethod.GET)
    public String backQueueUp(@RequestParam(value = "queueId", defaultValue = "0")long queueId,Model model){
        System.out.println("queueId="+queueId);
        model.addAttribute("queueId",queueId);
        return "queue/queueUp";
    }
    /**
     * 添加一条虚拟排队的记录
     * @param queueInfo
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String virtualQueue(QueueInfo queueInfo){
        JSONObject jsonObject = new JSONObject();
        try{
            queueInfo.setQueueStartTime(TimeFormatTool.getCurrentTime());
            long id = queueQueryService.save(queueInfo);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("id",id);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return  jsonObject.toJSONString();
    }

    /**
     * 更新虚拟排队信息
     * @param queueInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(QueueInfo queueInfo){
        System.out.println(queueInfo);
        JSONObject jsonObject = new JSONObject();
        List<TableType> tableTypeList = null;
        try{
            //根据用餐人数获取桌位信息
            tableTypeList=tableService.getTableTypeByEatNum(queueInfo.getEatNumber());
            //根据桌型id获得排队人数
//            eatCountById= queueQueryService.getWaitCountById(queueInfo.getId(),tableTypeList.get(0).getId());
            //根据座位号获取餐桌信息
            if(!"".equals(queueInfo.getSeatNum()) && queueInfo.getSeatNum() != null){
                TableNumber tableNumber = tableService.getByTableName(queueInfo.getSeatNum());
                queueInfo.setTableNumber(tableNumber);
            }
            queueInfo.setTableType(tableTypeList.get(0));
            queueInfo.setQueueState("0");
            queueInfo.setQueueStartTime(TimeFormatTool.getCurrentTime());
            //更新排队
            queueQueryService.update(queueInfo);
            WaitTimeModel waitTimeModel = calculateWaitTime(queueInfo.getId(),tableTypeList.get(0).getId(),queueInfo.getSeatFlag());
            waitTimeModel.setTableType(tableTypeList.get(0));

            List<TableNumber> tableNumberList =  tableService.findByTableTypeId(tableTypeList.get(0).getId());

            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("queueInfo",waitTimeModel);
            jsonObject.put("tableNumInfo",tableNumberList);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    /**
     * 确认排队
     * @param queueId
     * @param tel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/confirmQueue", method = RequestMethod.GET)
    public String getTime(@RequestParam(value = "queueId", defaultValue = "0")long queueId,
                          @RequestParam(value = "tel", defaultValue = "")String tel){
        JSONObject jsonObject = new JSONObject();
        try{
            queueQueryService.updateStateAndTel(queueId,tel,"1");
            QueueInfo queueInfo = queueQueryService.findById(queueId);
            WaitTimeModel waitTimeModel = calculateWaitTime(queueId,queueInfo.getTableType().getId(),queueInfo.getSeatFlag());
            waitTimeModel.setTableType(queueInfo.getTableType());
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("queueInfo",waitTimeModel);
        }catch (Exception e){
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }

    /**
     * 取消排队
     * @param queueId
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
   public void cancel(@RequestParam(value = "queueId", defaultValue = "0")long queueId){
        try{
            queueQueryService.delete(queueId);
        }catch (Exception e){
            e.printStackTrace();
        }

   }

    /**
     * 根据id查询排队状况
     * @param checkTimeCo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTime", method = RequestMethod.POST)
    public String getTime(CheckTimeCo checkTimeCo){
        List<TableType> tableTypeList = null;
        JSONObject jsonObject = new JSONObject();
        CalculateTimeVO calculateTimeVO = new CalculateTimeVO();
        WaitTimeModel waitTimeModel = new WaitTimeModel();
        Long eatCountById = 0L;
        try{
            //根据用餐人数获取桌位信息
            tableTypeList=tableService.getTableTypeByEatNum(checkTimeCo.getEatNumber());
            //根据桌型id获得排队人数
            eatCountById= queueQueryService.getWaitCountById(checkTimeCo.getId(),tableTypeList.get(0).getId());
            System.out.println("eatCountById="+eatCountById);
            calculateTimeVO.setTableTypeId(tableTypeList.get(0).getId());
            calculateTimeVO.setSeatNum(checkTimeCo.getSeatNum());
            calculateTimeVO.setEatCountById(eatCountById);
            calculateTimeVO.setLastTableTime("20170906151554");
//            waitTimeModel.setWaitTime(calculateWaitTime(calculateTimeVO));
            waitTimeModel.setTableType(tableTypeList.get(0));
            waitTimeModel.setQueueId(checkTimeCo.getId());
            waitTimeModel.setWaitPopulation(eatCountById);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("queueInfo",waitTimeModel);

        }catch(Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    /**
     *
     * 时间算法
     * @param
     * @return
     */
    private  WaitTimeModel calculateWaitTime(long queueId,long tableTypeId,Boolean seatFlag){
        WaitTimeModel waitTimeModel = new WaitTimeModel();

        //设定的每桌用餐时间，单位默认是ms
        long eachTableTime = 10 * 60 * 1000;
        //接收排队等待时间
        long waitTime = 0L;
        try{
            //根据桌型id获得排队人数
            long eatCountById= queueQueryService.getWaitCountById(queueId,tableTypeId);
            long chooseCount = queueQueryService.chooseSeatCountById(queueId,tableTypeId);
            //根据桌型id拿出该桌型下桌子的数量
            Integer seatCount = tableService.getTableCountByType(tableTypeId);

            System.out.println(seatCount);
            //判断顾客是否选坐
            if(seatFlag){
                //选座时间计算
                waitTime = (chooseCount+1)*eachTableTime +(eatCountById -chooseCount)*eachTableTime/seatCount;
            }else{
                //未选座时间计算
                waitTime = chooseCount*eachTableTime +(eatCountById -chooseCount +1)*eachTableTime/seatCount;
            }
            //减去最后一近顾客就餐距离当前的差
            if(TimeFormatTool.diffTime(queueHistoryService.getLastTime()) < 600000){
                waitTime = waitTime - TimeFormatTool.diffTime(queueHistoryService.getLastTime());
            }else{
                waitTime = waitTime - 600000;
            }
            //将单位换算成分钟
            waitTime = waitTime /(1000 *60);
            waitTimeModel.setWaitTime(waitTime);
            waitTimeModel.setQueueId(queueId);
            waitTimeModel.setWaitPopulation(eatCountById);
        }catch (Exception e){
            e.printStackTrace();
        }
        return waitTimeModel;
    }
}
