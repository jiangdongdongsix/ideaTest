package com.iqes.web.queue;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableType;
import com.iqes.entity.co.CalculateTimeVO;
import com.iqes.entity.co.CheckTimeCo;
import com.iqes.entity.vo.WaitTimeModel;
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


    /**
     * 返回直立机主页面
     * @return String
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String backQueueHome(){
        return "queue/queueHome";
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
        JSONObject jsonObject = new JSONObject();
        WaitTimeModel waitTimeModel = new WaitTimeModel();
        List<TableType> tableTypeList = null;
        try{
            //根据用餐人数获取桌位信息
            tableTypeList=tableService.getTableTypeByEatNum(queueInfo.getEatNumber());
            waitTimeModel.setTableType(tableTypeList.get(0).getTableTypeName());
            waitTimeModel.setWaitPopulation(10);
            waitTimeModel.setWaitTime(20);
            queueInfo.setTableType(tableTypeList.get(0));
            queueQueryService.update(queueInfo);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("queueInfo",waitTimeModel);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/getTime", method = RequestMethod.POST)
    public String getTime(CheckTimeCo checkTimeCo){
        List<TableType> tableTypeList = null;
        JSONObject jsonObject = new JSONObject();
        CalculateTimeVO calculateTimeVO = new CalculateTimeVO();
        WaitTimeModel waitTimeModel = new WaitTimeModel();
        long eatCountById = 0;
        try{
            //根据用餐人数获取桌位信息
            tableTypeList=tableService.getTableTypeByEatNum(checkTimeCo.getEatNumber());
            //根据桌型id获得排队人数
            eatCountById= queueQueryService.getWaitCountById(checkTimeCo.getId(),tableTypeList.get(0).getId());
            calculateTimeVO.setTableTypeId(tableTypeList.get(0).getId());
            calculateTimeVO.setSeatNum(checkTimeCo.getSeatNum());
            calculateTimeVO.setEatCountById(eatCountById);
            calculateTimeVO.setLastTableTime("20170906151554");
            waitTimeModel.setWaitTime(calculateWaitTime(calculateTimeVO));
            waitTimeModel.setTableType(tableTypeList.get(0).getTableTypeName());
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
     * @param calculateTimeVO
     * @return
     */
    public  Long calculateWaitTime(CalculateTimeVO calculateTimeVO){
        //设定的每桌用餐时间，单位默认是ms
        long eachTableTime = 10 * 60 * 1000;
        //接收排队等待时间
        long waitTime = 0L;
        try{
            //根据桌型id拿出该桌型下桌子的数量
            Integer seatCount = tableService.getTableCountByType(calculateTimeVO.getTableTypeId());
            //判断顾客是否选坐
            if(calculateTimeVO.getSeatNum() == null || "".equals(calculateTimeVO.getSeatNum())){
                //未选座时间计算
                waitTime = calculateTimeVO.getEatCountById()*eachTableTime + eachTableTime/seatCount;
            }else{
                //选座时间计算
                waitTime = calculateTimeVO.getEatCountById()*eachTableTime + eachTableTime;
            }
//            System.out.println("waitTime1="+waitTime);
//            System.out.println(TimeFormatTool.diffTime(calculateTimeVO.getLastTableTime()));
            //减去最后一近顾客就餐距离当前的差
            waitTime = waitTime - TimeFormatTool.diffTime(calculateTimeVO.getLastTableTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return waitTime;
    }


    /**时间算法测试
    public static void main(String[] args){
        CalculateTimeVO calculateTimeVO = new CalculateTimeVO();
        calculateTimeVO.setEatCountById(5);
        calculateTimeVO.setLastTableTime("20170901154554");
        calculateTimeVO.setSeatNum("05");
        calculateTimeVO.setTableTypeId(1);
        System.out.println(calculateWaitTime(calculateTimeVO));
    }**/
}
