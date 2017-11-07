package com.iqes.web.restaurant;

/**
 * @author huqili
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iqes.entity.TableType;
import com.iqes.entity.dto.TableTypeDTO;
import com.iqes.service.restaurant.TableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


        JSONObject jsonObject=new JSONObject();

        try {
            tableTypeService.deleteOne(id);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String findAll(){
        JSONObject jsonObject=new JSONObject();
        List<TableType> tableTypes;

        try {
            tableTypes=tableTypeService.findAll();
            jsonObject.put("tableTypes",tableTypes);
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "0");
            jsonObject.put("ErrorMessage", "");
        }catch (Exception e){
            jsonObject.put("Version", "1.0");
            jsonObject.put("ErrorCode", "1");
            jsonObject.put("ErrorMessage", e.getMessage());
            e.printStackTrace();
        }

//        String json= JSON.toJSONString(jsonObject, SerializerFeature.WRITE_MAP_NULL_FEATURES);
//        return json;
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/eatTime",method = RequestMethod.POST)
    public String updateEatTime(@RequestParam(value = "tableTypeId")Long tableTypeId,@RequestParam(value = "eatTime")Integer eatTime){
        JSONObject jsonObject=new JSONObject();
        try {
            String msg=tableTypeService.updateEatTime(tableTypeId,eatTime);
            jsonObject.put("msg",msg);
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
    @RequestMapping(value = "/queue",method = RequestMethod.GET)
    public String getAllTableTypeQueueInfo(){
        JSONObject jsonObject=new JSONObject();

        List<TableTypeDTO> tableTypeDTOS = null;

        try {
            tableTypeDTOS=tableTypeService.getSomethingAboutTableType();
            jsonObject.put("tableTypeDTOs",tableTypeDTOS);
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
