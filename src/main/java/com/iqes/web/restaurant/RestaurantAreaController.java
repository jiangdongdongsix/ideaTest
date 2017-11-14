package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.RestaurantArea;
import com.iqes.service.restaurant.RestaurantAreaService;
import com.iqes.service.restaurant.TableNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 54312
 */
@Controller
@RequestMapping(value = "restaurant/area")
public class RestaurantAreaController {

    @Autowired
    private RestaurantAreaService restaurantAreaService;

    @Autowired
    private TableNumberService tableNumberService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveOne(@RequestBody RestaurantArea restaurantArea){

        JSONObject jsonObject=new JSONObject();

        try {
            restaurantAreaService.saveOne(restaurantArea);
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
    @RequestMapping(method = RequestMethod.DELETE)
    public String saveOne(@RequestParam(value = "id")Long id){

        JSONObject jsonObject=new JSONObject();

        try {
            restaurantAreaService.deleteOne(id);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
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
    public String findOne(@RequestParam(value = "id")Long id){

        JSONObject jsonObject=new JSONObject();
        RestaurantArea area;
        try {
            area=restaurantAreaService.findOne(id);
            jsonObject.put("area",area);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String findAll(){

        JSONObject jsonObject=new JSONObject();
        List<RestaurantArea> areas;
        try {
            areas=restaurantAreaService.findAll();
            jsonObject.put("areas",areas);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/tableNumber/amount/state")
    public String findTableNumberByStateAndArea(){

        JSONObject jsonObject=new JSONObject();
        Map<String,Integer> map=new HashMap<String, Integer>();

        try {
        List<RestaurantArea> areas=restaurantAreaService.findAll();
        for (RestaurantArea restaurantArea:areas){
            Integer amount=tableNumberService.findTableNumbersByArea(restaurantArea);
            map.put(restaurantArea.getAreaName(),amount);
        }
            jsonObject.put("map",map);
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","0");
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("Version","1.0");
            jsonObject.put("ErrorCode","1");
            jsonObject.put("ErrorMessage",e.getMessage());
        }
        return jsonObject.toJSONString();
    }
}
