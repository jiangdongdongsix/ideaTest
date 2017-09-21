package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.TableNumber;
import com.iqes.service.restaurant.TableNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "restaurant/tableNumber")
public class TableNumberController {

    @Autowired
    private TableNumberService tableNumberService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam("tableNumber")TableNumber tableNumber){
        tableNumberService.saveOne(tableNumber);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String findOne(@RequestParam("id") Long id){

        JSONObject jsonObject=new JSONObject();
        TableNumber tableNumber=tableNumberService.findById(id);

        jsonObject.put("tableNumber",tableNumber);

        return jsonObject.toJSONString();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteOne(@RequestParam("id")Long id){
        tableNumberService.deleteOne(id);
    }
}
