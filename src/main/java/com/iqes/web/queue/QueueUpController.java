package com.iqes.web.queue;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.QueueInfo;
import com.iqes.entity.TableType;
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

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String backQueueHome(){
        return "queue/queueHome";
    }

    @RequestMapping(value = "/lineup", method = RequestMethod.GET)
    public String backQueueUp(@RequestParam(value = "queueId", defaultValue = "0")long queueId,Model model){
        System.out.println("queueId="+queueId);
        model.addAttribute("queueId",queueId);
        return "queue/queueUp";
    }


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
            waitTimeModel.setWaitTime("20");
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
        try{
            //根据用餐人数获取桌位信息
            tableTypeList=tableService.getTableTypeByEatNum(checkTimeCo.getEatNumber());


        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }


}
