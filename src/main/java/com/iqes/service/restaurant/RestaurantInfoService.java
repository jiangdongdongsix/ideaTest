package com.iqes.service.restaurant;


import com.iqes.entity.RestaurantInfo;
import com.iqes.repository.restaurant.RestaurantInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RestaurantInfoService {

    @Autowired
    private RestaurantInfoDao restaurantInfoDao;

    public void saveOne(RestaurantInfo restaurantInfo){
        restaurantInfoDao.save(restaurantInfo);
    }

    public RestaurantInfo findOne(){
        return restaurantInfoDao.findOne((long)1);
    }
}
