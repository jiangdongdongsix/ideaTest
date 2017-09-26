package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.TableType;
import com.iqes.service.restaurant.TableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/restaurant/tableType")
public class TableTypeController {

    @Autowired
    private TableTypeService tableTypeService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam("tableType")TableType tableType){
        tableTypeService.saveOne(tableType);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String findOne(@RequestParam("id") Long id){

        JSONObject jsonObject=new JSONObject();
        TableType tableType=tableTypeService.findById(id);

        jsonObject.put("tableType",tableType);

        return jsonObject.toJSONString();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteOne(@RequestParam("id")Long id){
        tableTypeService.deleteOne(id);
    }
}
