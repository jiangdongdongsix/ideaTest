package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.RestaurantInfo;
import com.iqes.service.restaurant.RestaurantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/restaurant/restaurantInfo")
public class RestaurantInfoController {

    @Autowired
    private RestaurantInfoService restaurantInfoService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam(value = "restaurantInfo")RestaurantInfo restaurantInfo){
        restaurantInfoService.saveOne(restaurantInfo);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String find(){

        JSONObject jsonObject=new JSONObject();

        RestaurantInfo restaurantInfo=restaurantInfoService.findOne();
        jsonObject.put("restaurantInfo",restaurantInfo);

        return jsonObject.toJSONString();
    }
}
