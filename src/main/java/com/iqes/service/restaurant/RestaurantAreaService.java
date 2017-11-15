package com.iqes.service.restaurant;

import com.iqes.entity.RestaurantArea;
import com.iqes.repository.restaurant.RestaurantAreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 54312
 *
 */
@Service
@Transactional
public class RestaurantAreaService {

    @Autowired
    private RestaurantAreaDao restaurantAreaDao;

    public void saveOne(RestaurantArea restaurantArea){
        if (restaurantArea==null){
            return;
        }
        restaurantAreaDao.save(restaurantArea);
    }

    public void deleteOne(Long id){
        RestaurantArea restaurantArea=restaurantAreaDao.findOne(id);
        if (restaurantArea==null){
            return;
        }else {
            restaurantAreaDao.delete(id);
        }

    }

    public List<RestaurantArea> findAll(){
      return  restaurantAreaDao.findAll();
    }

    public RestaurantArea findOne(Long id){
        return restaurantAreaDao.findOne(id);
    }
 }
