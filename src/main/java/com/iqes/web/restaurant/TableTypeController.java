package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.TableType;
import com.iqes.service.restaurant.TableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/restaurant/tableType")
public class TableTypeController {

    @Autowired
    private TableTypeService tableTypeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String save(@RequestBody TableType tableType){

        JSONObject jsonObject=new JSONObject();

        if (tableType!=null){
            tableTypeService.saveOne(tableType);
            jsonObject.put("msg","保存成功");
        }else{
            jsonObject.put("msg","传递信息为空");
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String findOne(@RequestParam("id") Long id){

        JSONObject jsonObject=new JSONObject();
        TableType tableType=tableTypeService.findById(id);

        if (tableType!=null){
            jsonObject.put("tableType",tableType);
        }else {
            jsonObject.put("msg","查无此桌");
        }

        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteOne(@RequestParam("id")Long id){
        String msg=tableTypeService.deleteOne(id);

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("msg",msg);

        return jsonObject.toJSONString();
    }
}
