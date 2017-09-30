package com.iqes.web.queue;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.QueueInfo;
import com.iqes.service.queue.ExtractNumberService;
import com.iqes.service.queue.QueryNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        } catch (Exception e) {
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

        QueueInfo queueInfo = null;

        try {
            queueInfo = queryNumberService.queryNumber();
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
            jsonObject.put("queryNumber", queueInfo);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
        }
            return jsonObject.toJSONString();
    }

    /**
     * 删除排队顾客记录，添加到历史记录表里
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
}
