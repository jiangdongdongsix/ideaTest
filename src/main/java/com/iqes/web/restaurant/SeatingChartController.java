package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.SeatingChart;
import com.iqes.service.restaurant.SeatingChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 54312
 */
@Controller
public class SeatingChartController {

    @Autowired
    private SeatingChartService seatingChartService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String save(MultipartFile file, HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();

        try{
           seatingChartService.saveOne(file,request);
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
        SeatingChart seatingChart;

        try{
            seatingChart=seatingChartService.find();
            jsonObject.put("seatingChart",seatingChart);
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
