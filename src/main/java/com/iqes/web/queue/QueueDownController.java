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

@Controller
@RequestMapping(value = "queue")
public class QueueDownController {

    @Autowired
    private ExtractNumberService extractNumberService;

    @Autowired
    private QueryNumberService queryNumberService;

    @RequestMapping(value = "arrivingCustomer",method = RequestMethod.GET)
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

    @RequestMapping(value = "queryNumber",method = RequestMethod.GET)
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
}
