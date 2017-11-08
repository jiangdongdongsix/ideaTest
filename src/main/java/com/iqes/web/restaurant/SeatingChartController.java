package com.iqes.web.restaurant;

import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.SeatingChart;
import com.iqes.service.restaurant.SeatingChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 54312
 */
@Controller
@RequestMapping(value = "/restaurant/seatingChart")
public class SeatingChartController {

    @Autowired
    private SeatingChartService seatingChartService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String save(@RequestParam(value = "file", required = false)MultipartFile file, HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        System.out.println("餐厅座位图上传！！！");

        try{
           String url=seatingChartService.saveOne(file,request);
           jsonObject.put("url",url);
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

        try{
            String chartUrl=seatingChartService.findOne();
            jsonObject.put("chartUrl",chartUrl);
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
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String findAll(){
        JSONObject jsonObject=new JSONObject();
        List<SeatingChart> seatingCharts;

        try{
            seatingCharts=seatingChartService.findAll();
            jsonObject.put("seatingCharts",seatingCharts);
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
