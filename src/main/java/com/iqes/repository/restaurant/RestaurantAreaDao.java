package com.iqes.repository.restaurant;

import com.iqes.entity.RestaurantArea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 54312
 */
public interface RestaurantAreaDao extends CrudRepository<RestaurantArea,Long> {

    RestaurantArea findByAreaName(String areaName);


    @Override
    @Query(value = "select area from RestaurantArea as area order by area.id desc ")
    List<RestaurantArea > findAll();
}
