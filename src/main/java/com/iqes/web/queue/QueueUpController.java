package com.iqes.web.queue;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.*;
import com.iqes.entity.vo.WaitTimeModel;
import com.iqes.service.ServiceException;
import com.iqes.service.queue.ExtractNumberService;
import com.iqes.service.queue.QueueHistoryService;
import com.iqes.service.queue.QueueQueryService;
import com.iqes.service.queue.TableService;
import com.iqes.service.restaurant.ConfigInfoService;
import com.iqes.utils.TimeFormatTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    private ConfigInfoService configInfoService;


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
    @RequestMapping(value = "/virtualqueue", method = RequestMethod.POST)
    public String virtualQueue(@RequestBody QueueInfo queueInfo){
        System.out.println(queueInfo);
        System.out.println("虚拟排队");
        JSONObject jsonObject = new JSONObject();
        try{
            if (configInfoService.findPauseQueue()){
                throw new ServiceException("抱歉，本店已暂停抽号");
            }
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
        public String update(@RequestBody QueueInfo queueInfo){
        System.out.println("==========="+queueInfo);
        JSONObject jsonObject = new JSONObject();
        List<TableType> tableTypeList = null;
        try{
            //根据用餐人数获取桌位信息
            tableTypeList=tableService.getTableTypeByEatNum(queueInfo.getEatNumber());
            //根据桌型id获得排队人数
//            eatCountById= queueQueryService.getWaitCountById(queueInfo.getId(),tableTypeList.get(0).getId());
            //根据座位号获取餐桌信息
            if(!"".equals(queueInfo.getSeatNum()) && null != queueInfo.getSeatNum()){
                TableNumber tableNumber = tableService.getByTableName(queueInfo.getSeatNum());
                queueInfo.setTableNumber(tableNumber);
            }
            queueInfo.setTableType(tableTypeList.get(0));
            queueInfo.setQueueId(tableTypeList.get(0).getTableTypeName()+queueInfo.getId());
            queueInfo.setQueueState("0");
            queueInfo.setExtractFlag("0");
            queueInfo.setExtractCount(0);
            queueInfo.setCallCount(0);
            queueInfo.setExFlag(false);
            queueInfo.setShareTalbeState("0");

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
    @RequestMapping(value = "/confirmqueue", method = RequestMethod.GET)
    public String getTime(@RequestParam(value = "queueId", defaultValue = "0")long queueId,
                          @RequestParam(value = "tel", defaultValue = "")String tel){
        JSONObject jsonObject = new JSONObject();
        try{
            queueQueryService.updateStateAndTel(queueId,tel,"1");
            QueueInfo queueInfo = queueQueryService.findById(queueId);
            WaitTimeModel waitTimeModel = calculateWaitTime(queueId,queueInfo.getTableType().getId(),queueInfo.getSeatFlag());
            waitTimeModel.setTableType(queueInfo.getTableType());
            waitTimeModel.setQueueId(queueId);
            waitTimeModel.setQueueStartTime(queueInfo.getQueueStartTime());
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
    @ResponseBody
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
   public String cancel(@RequestParam(value = "queueId", defaultValue = "0")long queueId){
        JSONObject jsonObject = new JSONObject();
        try{
            queueQueryService.delete(queueId);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("Msg","取消成功");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("Msg","取消失败");
        }

        return jsonObject.toJSONString();
   }

    /**
     * 查看个人排队
     * @param queueNumber
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/personalqueueinfo", method = RequestMethod.GET)
    public String checkPersonalQueueInfo(@RequestParam(value = "queueNumber", defaultValue = "0")String queueNumber) {
        JSONObject jsonObject = new JSONObject();
        long queueId = 0L;
        try {
//            if (queueNumber.length() > 2) {
                queueId = Long.parseLong(queueNumber.substring(1));
//            }else{
//                queueId = Long.parseLong(queueNumber);
//            }
            QueueInfo queueInfo = queueQueryService.findById(queueId);

            WaitTimeModel waitTimeModel = calculateWaitTime(queueId, queueInfo.getTableType().getId(), queueInfo.getSeatFlag());
            waitTimeModel.setTableType(queueInfo.getTableType());
            waitTimeModel.setExtractFlag(queueInfo.getExtractFlag());


            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
            jsonObject.put("queueInfo", waitTimeModel);
            jsonObject.put("extractFlag",queueInfo.getExtractFlag());
            System.out.println(jsonObject.toJSONString());

        } catch (Exception e) {
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }

    /**
     * 查看所有排队
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/allqueueinfo",method = RequestMethod.GET)
    public String checkAllQueueInfo(){
        System.out.println("进入checkAllQueueInfo");
        List<WaitTimeModel> waitTimeModelList = new ArrayList<WaitTimeModel>();
        JSONObject jsonObject = new JSONObject();
       try{
           List<TableType> tableTypeList = tableService.findAll();
           for(TableType tableType:tableTypeList){
               WaitTimeModel waitTimeModel = new WaitTimeModel();
               waitTimeModel = calculateALLQueueWaitTime(tableType.getId());
               waitTimeModel.setTableType(tableType);
               waitTimeModelList.add(waitTimeModel);
           }
           jsonObject.put("Version", "1.0");
           jsonObject.put("ErrorCode", "0");
           jsonObject.put("ErrorMessage", "");
           jsonObject.put("queueInfo", waitTimeModelList);
           System.out.println(jsonObject.toJSONString());

       }catch (Exception e){
           jsonObject.put("Version", "1.0");
           jsonObject.put("ErrorCode", "1");
           jsonObject.put("ErrorMessage", e.getMessage());
           e.printStackTrace();
       }
        return jsonObject.toString();
    }




    /**
     * 时间算法
     * 计算个人排队
     */
    private  WaitTimeModel calculateWaitTime(long queueId,long tableTypeId,Boolean seatFlag){
        WaitTimeModel waitTimeModel = new WaitTimeModel();
        //设定的每桌用餐时间，单位默认是ms
        long eachTableTime = 0L;
        //接收排队等待时间
        long waitTime = 0L;
        try{
            eachTableTime =  tableService.findEatTimeById(tableTypeId);
            eachTableTime = eachTableTime * 60 * 1000;
            //根据桌型id获得排队人数
            long eatCountById= queueQueryService.getWaitCountById(queueId,tableTypeId);
            long chooseCount = queueQueryService.chooseSeatCountById(queueId,tableTypeId);
            //根据桌型id拿出该桌型下桌子的数量
            Integer seatCount = tableService.getTableCountByType(tableTypeId);
            long eachTableTimeBySeatCount = eachTableTime/seatCount;

            if(seatCount >0){
                //判断顾客是否选坐
                if(seatFlag){
                    //选座时间计算
                    waitTime = (chooseCount+1)*eachTableTime +(eatCountById -chooseCount)*eachTableTime/seatCount;
                }else{
                    //未选座时间计算
                    waitTime = chooseCount*eachTableTime +(eatCountById -chooseCount +1)*eachTableTime/seatCount;
                }
            }else{
                //  待处理
                waitTime = eatCountById * eachTableTime;
            }

            if(null != queueHistoryService.getLastTime(tableTypeId) && !"".equals(queueHistoryService.getLastTime(tableTypeId)) ){
                //减去最后一近顾客就餐距离当前的差
                if(TimeFormatTool.diffTime(queueHistoryService.getLastTime(tableTypeId)) < eachTableTimeBySeatCount){
                    waitTime = waitTime - TimeFormatTool.diffTime(queueHistoryService.getLastTime(tableTypeId));
                }
            }

//            else{
//                waitTime = waitTime - eachTableTime;
//            }
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

    /**
     *
     * 时间算法
     * 计算整个队形
     */
    private  WaitTimeModel calculateALLQueueWaitTime(long tableTypeId){
        WaitTimeModel waitTimeModel = new WaitTimeModel();
        //设定的每桌用餐时间，单位默认是ms
        long eachTableTime = 0L;
        //接收排队等待时间
        long waitTime = 0L;
        try{
            eachTableTime =  tableService.findEatTimeById(tableTypeId);
            eachTableTime = eachTableTime * 60 * 1000;
            //根据桌型id获得排队人数
            long eatCountById= queueQueryService.getWaitCount(tableTypeId);
            long chooseCount = queueQueryService.chooseSeatCountByTableTypeId(tableTypeId);
            //根据桌型id拿出该桌型下桌子的数量
            Integer seatCount = tableService.getTableCountByType(tableTypeId);
            long eachTableTimeBySeatCount = eachTableTime/seatCount;


            if(eatCountById > 0){

                System.out.println(seatCount);
                if(seatCount >0){
                    waitTime = chooseCount*eachTableTime +(eatCountById -chooseCount)*eachTableTime/seatCount;
                }else{
                    //  待处理
                    waitTime = eatCountById * eachTableTime;
                }


                if(null != queueHistoryService.getLastTime(tableTypeId) && !"".equals(queueHistoryService.getLastTime(tableTypeId)) ){
                    //减去最后一近顾客就餐距离当前的差
                    if(TimeFormatTool.diffTime(queueHistoryService.getLastTime(tableTypeId)) < eachTableTimeBySeatCount){
                        waitTime = waitTime - TimeFormatTool.diffTime(queueHistoryService.getLastTime(tableTypeId));
                    }
//                else{
//                    waitTime = waitTime - eachTableTime;
//                }
                }

                //将单位换算成分钟
                waitTime = waitTime /(1000 *60);
            }

            waitTimeModel.setWaitTime(waitTime);
            waitTimeModel.setWaitPopulation(eatCountById);
        }catch (Exception e){
            e.printStackTrace();
        }
        return waitTimeModel;
    }
    
}
