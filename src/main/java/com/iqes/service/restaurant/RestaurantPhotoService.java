package com.iqes.service.restaurant;

import com.iqes.entity.RestaurantPhoto;
import com.iqes.repository.restaurant.RestaurantPhotoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestaurantPhotoService {

    @Autowired
    private RestaurantPhotoDao restaurantPhotoDao;

    public void saveOne(RestaurantPhoto restaurantPhoto){
        restaurantPhotoDao.save(restaurantPhoto);
    }

    public List<RestaurantPhoto> findAll(){
        return restaurantPhotoDao.findAll();
    }

    public void deleteOne(Long id){
        restaurantPhotoDao.delete(id);
    }
}
