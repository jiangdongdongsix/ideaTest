package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.ConfigInfo;
import com.iqes.service.restaurant.ConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 54312
 *
 */
@Controller
@RequestMapping(value = "/restaurant/configInfo")
public class ConfigInfoController {

    @Autowired
    private ConfigInfoService configInfoService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String  save(@RequestBody ConfigInfo configInfo){
        System.out.println("aaaaaaaaaaaaaaaaaa");
        System.out.println(configInfo.toString());
        JSONObject jsonObject=new JSONObject();
        try{
            configInfoService.saveOne(configInfo);
            jsonObject.put("configInfo",configInfo);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }

        return jsonObject.toJSONString();
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String find(){

        JSONObject jsonObject=new JSONObject();

        try{
            ConfigInfo configInfo=configInfoService.findOne();
            jsonObject.put("configInfo",configInfo);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }

        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/pause",method = RequestMethod.PATCH)
    @ResponseBody
    public String updatePauseQueue(@RequestParam("pause")boolean pause){
        JSONObject jsonObject=new JSONObject();
        try{
            configInfoService.upadtePause(pause);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = "/pause",method = RequestMethod.GET)
    @ResponseBody
    public String findPauseQueue(){
        JSONObject jsonObject=new JSONObject();
        try{
            boolean pause=configInfoService.findPauseQueue();
            jsonObject.put("pause",pause);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

}
