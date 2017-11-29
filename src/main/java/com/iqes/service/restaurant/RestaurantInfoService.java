package com.iqes.service.restaurant;


import com.alibaba.fastjson.JSONObject;
import com.iqes.entity.RestaurantInfo;
import com.iqes.rabbitmq.EmitLog;
import com.iqes.rabbitmq.RabbitCarrier;
import com.iqes.repository.restaurant.RestaurantInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 54312
 */

@Service
@Transactional
public class RestaurantInfoService {

    @Autowired
    private RestaurantInfoDao restaurantInfoDao;

    public void saveOne(RestaurantInfo restaurantInfo) throws Exception {

        RestaurantInfo r=restaurantInfoDao.findOne(restaurantInfo.getId());
        if (restaurantInfo.getCloudId()==null){
            restaurantInfo.setCloudId(r.getCloudId());
        }
        restaurantInfoDao.save(restaurantInfo);

        RabbitCarrier rabbitCarrier=new RabbitCarrier();
        rabbitCarrier.setServiceName("restaurantInfoService");
        rabbitCarrier.setMethodName("updateInfo");
        rabbitCarrier.setParameter(JSONObject.toJSONString(restaurantInfo));

        new EmitLog().send(JSONObject.toJSONString(rabbitCarrier));
    }

    public RestaurantInfo findOne(){
        return restaurantInfoDao.findOne((long)1);
    }

    public void testSaveOne(MultipartFile file) throws IOException, TimeoutException {
        System.out.println("上传测试图片");

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("file",file);

        RabbitCarrier rabbitCarrier=new RabbitCarrier();
        rabbitCarrier.setServiceName("TestService");
        rabbitCarrier.setMethodName("uploadPhoto");
        rabbitCarrier.setParameter(JSONObject.toJSONString(jsonObject));

        try {
            new EmitLog().send(JSONObject.toJSONString(rabbitCarrier));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
