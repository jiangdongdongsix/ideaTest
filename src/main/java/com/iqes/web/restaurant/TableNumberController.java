package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.TableNumber;
import com.iqes.service.restaurant.TableNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/restaurant/tableNumber")
public class TableNumberController {

    @Autowired
    private TableNumberService tableNumberService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestParam("tableNumber")TableNumber tableNumber){
        tableNumberService.saveOne(tableNumber);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String findOne(@RequestParam("id") Long id){

        JSONObject jsonObject=new JSONObject();
        TableNumber tableNumber=tableNumberService.findById(id);

        if (tableNumber!=null){
            jsonObject.put("tableNumber",tableNumber);
            jsonObject.put("msg","查找成功");
        }else{
            jsonObject.put("msg","查无此桌");
        }


        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteOne(@RequestParam("id")Long id){
        JSONObject jsonObject=new JSONObject();
        String msg=tableNumberService.deleteOne(id);

        jsonObject.put("msg",msg);

        return  jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/page/tableNumbers")
    public String getPageOfAllTableNumbers(@RequestParam(value = "pageNo")int pageNo,@RequestParam(value = "pageSize")int pageSize){

        JSONObject jsonObject=new JSONObject();

        try {
            Page<TableNumber> queueInfoList=tableNumberService.pageQuery(pageNo,pageSize);
            jsonObject.put("page",queueInfoList);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/page/tableNumbers/area")
    public String getPageOfAllTableNumbersByarea(@RequestParam(value = "pageNo")int pageNo,@RequestParam(value = "pageSize")int pageSize,@RequestParam(value = "area")String area){

        JSONObject jsonObject=new JSONObject();

        try {
            Page<TableNumber> queueInfoList=tableNumberService.pageQueryByArea(pageNo,pageSize,area);
            jsonObject.put("page",queueInfoList);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/page/tableNumbers/tableTypeName")
    public String getPageOfAllTableNumbersByTabbleTypeName(@RequestParam(value = "pageNo")int pageNo,@RequestParam(value = "pageSize")int pageSize,@RequestParam(value = "tableTypeName")String tableTypeName){

        JSONObject jsonObject=new JSONObject();

        try {
            Page<TableNumber> queueInfoList=tableNumberService.pageQueryByTableType(pageNo,pageSize,tableTypeName);
            jsonObject.put("page",queueInfoList);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }
}
