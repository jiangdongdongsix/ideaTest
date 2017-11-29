package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.Menu;
import com.iqes.entity.RestaurantInfo;
import com.iqes.service.restaurant.RestaurantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/restaurant/restaurantInfo")
public class RestaurantInfoController {

    @Autowired
    private RestaurantInfoService restaurantInfoService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String save(@RequestBody RestaurantInfo restaurantInfo){


        JSONObject jsonObject=new JSONObject();

        try{
            restaurantInfoService.saveOne(restaurantInfo);
            jsonObject.put("restaurantInfo",restaurantInfo);
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
        RestaurantInfo restaurantInfo;

        try{
            restaurantInfo=restaurantInfoService.findOne();
            jsonObject.put("restaurantInfo",restaurantInfo);
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
    @RequestMapping(value = "/testUpload",method = RequestMethod.POST)
    public String testUpload(@RequestParam(value = "file", required = false)MultipartFile file){
        JSONObject jsonObject=new JSONObject();
        System.out.println("餐厅座位图上传！！！");

        try{
            restaurantInfoService.testSaveOne(file);
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
