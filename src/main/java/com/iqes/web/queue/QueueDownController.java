package com.iqes.web.queue;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.QueueInfo;
import com.iqes.service.ServiceException;
import com.iqes.service.queue.ExtractNumberService;
import com.iqes.service.queue.QueryNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 抽号服务和叫号服务
 *
 * @author huqili.tp
 */
@Controller
@RequestMapping(value = "/queue")
public class QueueDownController {

    @Autowired
    private ExtractNumberService extractNumberService;

    @Autowired
    private QueryNumberService queryNumberService;

    /**
     *
     * 抽号服务
     * 输入空桌名字，返回排在最前面的排队号
     * @param tableName
     * @return String
     */

    @ResponseBody
    @RequestMapping(value = "/arrivingCustomer",method = RequestMethod.GET)
    public String extractNumber(@RequestParam("tableName")String tableName)  {

        JSONObject jsonObject=new JSONObject();

        QueueInfo queueInfo= null;
        try {
            queueInfo = extractNumberService.extractNumber(tableName);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
            jsonObject.put("extractNumber",queueInfo);
        }catch (ServiceException se){
            se.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",se.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }

        return jsonObject.toJSONString();
    }


    /**
     * 叫号查询
     * 主要是给直立机使用，采用轮询机制
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryNumber",method = RequestMethod.GET)
    public String queryNumber() {

        JSONObject jsonObject = new JSONObject();

        Map<String,List<QueueInfo>> listMap=null;

        try {
            listMap = queryNumberService.getArrivingNumberByTableType();
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
            jsonObject.put(" listMap",  listMap);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
        }
            return jsonObject.toJSONString();
    }

    /**
     * 删除排队顾客记录 ，添加到历史记录表里
     * @param qid
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)

    public String deleteNumber(@RequestParam("qid")Long qid){
        JSONObject jsonObject = new JSONObject();
        String respond = null;
        try {
            respond = extractNumberService.deleteNumberById(qid);
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonObject.put("Msg",respond);
        return jsonObject.toJSONString()    ;
    }

    /**
     *
     * @param pageNo
     * @param pageSize
     * @param tableTypeDescribe
     * @return 返回一个page对象
     */
    @ResponseBody
    @RequestMapping(value = "/page/queueNumbers/tableTypeDescribe",method = RequestMethod.GET)
    public String getPageOfNumber(@RequestParam ("pageNo") int pageNo ,@RequestParam("pageSize") int pageSize,@RequestParam("tableTypeDescribe") String tableTypeDescribe){

        JSONObject jsonObject=new JSONObject();

        try {
            Page<QueueInfo> queueInfoList=extractNumberService.pageQuery(pageNo,pageSize,tableTypeDescribe);
            jsonObject.put("page",queueInfoList);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }



    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String testController(){
        return "/testAPI";
    }

    @ResponseBody
    @RequestMapping(value = "/shareTable",method = RequestMethod.PATCH)
    public String shareTable(@RequestParam(value = "tableId")Long tableId,@RequestParam(value = "queueInfoIds")Long[] queueInfoIds){

        System.out.println("0000000000000");
        System.out.println(tableId);
        System.out.println(queueInfoIds);
        JSONObject jsonObject=new JSONObject();

        try {
            if (tableId==null||queueInfoIds==null){
                throw  new RuntimeException("传输内容为空！");
            }
            extractNumberService.shareTable(tableId,queueInfoIds);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }

        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/groupTable",method = RequestMethod.PATCH)
    public String shareTable(@RequestParam(value = "tableIds")Long[] tableIds,@RequestParam(value = "queueInfoId")Long queueInfoId){
        JSONObject jsonObject=new JSONObject();

        try {
            if (tableIds==null||queueInfoId==null){
                throw  new RuntimeException("传输内容为空！");
            }
            extractNumberService.groupTable(tableIds,queueInfoId);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }

        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/tableTypeDescribe",method = RequestMethod.GET)
    public String shareTable(@RequestParam(value = "tableTypeDescribe")String tableTypeDescribe){
        JSONObject jsonObject=new JSONObject();
        List<QueueInfo> queueInfos;
        try {
            if (tableTypeDescribe==null){
                throw  new RuntimeException("传输内容为空！");
            }
            queueInfos=extractNumberService.getQueueInfosByTableTypeDescribe(tableTypeDescribe);
            jsonObject.put("queueInfos",queueInfos);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }

//        String json= JSON.toJSONString(jsonObject, SerializerFeature.WRITE_MAP_NULL_FEATURES);
//        return json;
        return jsonObject.toJSONString();
    }

}
