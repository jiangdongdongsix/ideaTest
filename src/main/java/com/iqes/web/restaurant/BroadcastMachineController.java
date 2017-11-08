package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.BroadcastMachine;
import com.iqes.entity.dto.BroadcastHomePageDTO;
import com.iqes.service.restaurant.BroadcastMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 54312
 */
@Controller
@RequestMapping(value = "/restaurant/broadcastMachine")
public class BroadcastMachineController {

    @Autowired
    private BroadcastMachineService broadcastMachineService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveBroadcast(@RequestBody BroadcastMachine broadcastMachine){
        System.out.println("添加/修改 直立机信息");
        JSONObject jsonObject=new JSONObject();

        try {
            broadcastMachineService.save(broadcastMachine);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
            jsonObject.put("ErrorMessage","");
        }catch (Exception e){
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String findBroadcast(){

        JSONObject jsonObject=new JSONObject();
        try {
            BroadcastMachine broadcastMachine=broadcastMachineService.find();
            jsonObject.put("broadcastMachine",broadcastMachine);
            jsonObject.put("Vesison","1.0");
        }catch (Exception e){
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/homePage",method = RequestMethod.GET)
    public String getAllthingAboutBroadcast(){

        JSONObject jsonObject=new JSONObject();
        BroadcastHomePageDTO broadcastHomePage=null;
        try {
            broadcastHomePage=broadcastMachineService.getAllThingOfHomePage();
            jsonObject.put("broadcastHomePage",broadcastHomePage);
            jsonObject.put("ErrorCode","0");
            jsonObject.put("Vesison","1.0");
        }catch (Exception e){
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }
}
