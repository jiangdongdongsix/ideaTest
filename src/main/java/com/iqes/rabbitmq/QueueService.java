package com.iqes.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableNumber;
import com.iqes.entity.TableType;
import com.iqes.entity.vo.WaitTimeModel;
import com.iqes.service.queue.QueueHistoryService;
import com.iqes.service.queue.QueueQueryService;
import com.iqes.service.queue.TableService;
import com.iqes.service.restaurant.ConfigInfoService;
import com.iqes.utils.TimeFormatTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 54312
 * 排队service
 */
@Service
public class QueueService {

    @Autowired
    private ConfigInfoService configInfoService;

    @Autowired
    private QueueQueryService queueQueryService;

    @Autowired
    private TableService tableService;

    @Autowired
    private QueueHistoryService queueHistoryService;

    public JSONObject virtualQueue(String jsonData){

        QueueInfo queueInfo= JSON.parseObject(jsonData,QueueInfo.class);
        System.out.println(queueInfo);

        System.out.println("虚拟排队");
        JSONObject jsonObject = new JSONObject();
        try{
            if (configInfoService.findPauseQueue()){
                jsonObject.put("PauseFlag","1");
                return jsonObject;
            }
            queueInfo.setQueueStartTime(TimeFormatTool.getCurrentTime());
            long id = queueQueryService.save(queueInfo);
            jsonObject.put("PauseFlag","0");
            jsonObject.put("id",id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  jsonObject;
    }

    public JSONObject updateVirtual(String jsonData){

        QueueInfo queueInfo= JSON.parseObject(jsonData,QueueInfo.class);
        System.out.println("==========="+queueInfo);

        JSONObject jsonObject = new JSONObject();
        List<TableType> tableTypeList = null;
        try{
            //根据用餐人数获取桌位信息
            tableTypeList=tableService.getTableTypeByEatNum(queueInfo.getEatNumber());
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

            jsonObject.put("queueInfo",waitTimeModel);
            jsonObject.put("tableNumInfo",tableNumberList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject confirmQueue(String jsonData){
        JSONObject jsonObject = new JSONObject();
        try{
            JSONObject jsonDataObject = JSON.parseObject(jsonData);
            //利用键值对的方式获取到值
           //long queueId= (long) jsonDataObject.get("queueId");
            long queueId= Long.valueOf(jsonDataObject.get("queueId").toString());
            String tel= (String) jsonDataObject.get("tel");

            queueQueryService.updateStateAndTel(queueId,tel,"1");
            QueueInfo queueInfo = queueQueryService.findById(queueId);
            WaitTimeModel waitTimeModel = calculateWaitTime(queueId,queueInfo.getTableType().getId(),queueInfo.getSeatFlag());
            waitTimeModel.setTableType(queueInfo.getTableType());
            waitTimeModel.setQueueId(queueId);
            jsonObject.put("queueInfo",waitTimeModel);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject cancelQueue(String jsonData){
        JSONObject jsonObject = new JSONObject();
        try{
            JSONObject jsonDataObject = JSON.parseObject(jsonData);
           // Long queueId= (Long) jsonDataObject.get("queueId");
            long queueId= Long.valueOf(jsonDataObject.get("queueId").toString());
            queueQueryService.delete(queueId);
            jsonObject.put("Msg","取消成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject checkPersonalQueueInfo(String jsonData) {
        JSONObject jsonObject = new JSONObject();
        long queueId = 0L;
        try {
            JSONObject jsonDataObject = JSON.parseObject(jsonData);
            String queueNumber= (String) jsonDataObject.get("queueNumber");

            queueId = Long.parseLong(queueNumber.substring(1));
            QueueInfo queueInfo = queueQueryService.findById(queueId);


            WaitTimeModel waitTimeModel = calculateWaitTime(queueId, queueInfo.getTableType().getId(), queueInfo.getSeatFlag());
            waitTimeModel.setTableType(queueInfo.getTableType());
            waitTimeModel.setExtractFlag(queueInfo.getExtractFlag());

            jsonObject.put("queueInfo", waitTimeModel);
            jsonObject.put("extractFlag",queueInfo.getExtractFlag());
            System.out.println(jsonObject.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject checkAllQueueInfo(){
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
            jsonObject.put("queueInfo", waitTimeModelList);
            System.out.println("最终结果："+jsonObject.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

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
     * 从QueueUpController拷贝来的方法
     * @param tableTypeId
     * @return
     */
    private  WaitTimeModel calculateALLQueueWaitTime(long tableTypeId){
        WaitTimeModel waitTimeModel = new WaitTimeModel();
        long eachTableTime = 0L;
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
