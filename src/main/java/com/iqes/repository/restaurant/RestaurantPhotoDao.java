package com.iqes.repository.restaurant;

import com.iqes.entity.RestaurantPhoto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantPhotoDao extends CrudRepository<RestaurantPhoto,Long> {

    @Query("select rp from RestaurantPhoto rp")
    List<RestaurantPhoto> findAll();
}
