package com.iqes.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.TableType;
import com.iqes.entity.dto.MenuDTO;
import com.iqes.entity.dto.SimpleRestaurantInfo;
import com.iqes.entity.vo.WaitTimeModel;
import com.iqes.service.queue.QueueHistoryService;
import com.iqes.service.queue.QueueQueryService;
import com.iqes.service.queue.TableService;
import com.iqes.service.restaurant.MenuService;
import com.iqes.service.restaurant.RestaurantInfoService;
import com.iqes.utils.TimeFormatTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 54312
 * 手机端展示
 */
@Service
public class AppShowService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantInfoService restaurantInfoService;

    @Autowired
    private QueueService queueService;

    public JSONObject checkRestaurantInfoAndAllQueueInfo(){

        System.out.println("进入checkRestaurantInfoAndAllQueueInfo方法");
        JSONObject jsonObject=new JSONObject();

        SimpleRestaurantInfo simpleRestaurantInfo=new SimpleRestaurantInfo(
                restaurantInfoService.findOne().getName(),
                restaurantInfoService.findOne().getDetailAddress(),
                restaurantInfoService.findOne().getOpenTime(),
                restaurantInfoService.findOne().getEndTime()
        );

        JSONObject queueInfos=queueService.checkAllQueueInfo();

        jsonObject.put("restaurantInfo",simpleRestaurantInfo);
        jsonObject.put("queueInfos",queueInfos);

        return jsonObject;
    }

    public String test(){
        String testMessage= JSON.toJSONString(menuService.findOne((long)1)) ;
        return  testMessage;
    }

    public JSONObject getMenus(){
        JSONObject jsonObject=new JSONObject();
        List<MenuDTO> menuDTOS=menuService.getMenusAvailableIsTrue();
        jsonObject.put("menus",menuDTOS);
        return jsonObject;
    }
}
