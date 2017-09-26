package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.RestaurantPhoto;
import com.iqes.service.restaurant.RestaurantPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/restaurant/restaurantPhoto")
public class RestaurantPhotoController {

    @Autowired
    private RestaurantPhotoService restaurantPhotoService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam("restaurantPhoto")RestaurantPhoto restaurantPhoto){
        restaurantPhotoService.saveOne(restaurantPhoto);
    }

    @RequestMapping(value = "/Photos",method = RequestMethod.GET)
    public String findAll(){

        JSONObject jsonObject=new JSONObject();

        List<RestaurantPhoto> restaurantPhotoList=restaurantPhotoService.findAll();

        jsonObject.put("restaurantPhotoList",restaurantPhotoList);

        return jsonObject.toJSONString();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestParam("id") Long id){
        restaurantPhotoService.deleteOne(id);
    }
}
