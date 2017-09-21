package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.ConfigInfo;
import com.iqes.service.restaurant.ConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "restaurant/configInfo")
public class ConfigInfoController {

    @Autowired
    private ConfigInfoService configInfoService;

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public void save(@RequestParam(value = "configInfo")ConfigInfo configInfo){
        configInfoService.saveOne(configInfo);
    }

    @RequestMapping(value = "find",method = RequestMethod.GET)
    public String find(){

        JSONObject jsonObject=new JSONObject();

        ConfigInfo configInfo=configInfoService.findOne();
        jsonObject.put("configInfo",configInfo);

        return jsonObject.toJSONString();
    }

}
