package com.iqes.service.restaurant;

import com.iqes.entity.BroadcastMachine;
import com.iqes.entity.RestaurantPhoto;
import com.iqes.entity.dto.BroadcastHomePageDTO;
import com.iqes.repository.restaurant.BroadcastMachineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 54312
 */
@Service
@Transactional
public class BroadcastMachineService {

    @Autowired
    private BroadcastMachineDao broadcastMachineDao;

    @Autowired
    private RestaurantPhotoService restaurantPhotoService;

    public void save(BroadcastMachine broadcastMachine){
        broadcastMachineDao.save(broadcastMachine);
    }

    public BroadcastMachine find(){
        return broadcastMachineDao.findOne((long)1);
    }

    public BroadcastHomePageDTO getAllThingOfHomePage(){
        BroadcastHomePageDTO broadcastHomePage=new BroadcastHomePageDTO();
        List<String> stringList=new ArrayList<String>();


        List<RestaurantPhoto> restaurantPhotoList=restaurantPhotoService.getPhotosByArea("1");


        for (RestaurantPhoto restaurantPhoto:restaurantPhotoList){
            System.out.println(restaurantPhoto.getUrl());
            stringList.add(restaurantPhoto.getUrl());
        }

        broadcastHomePage.setPhotoUrls(stringList);
        broadcastHomePage.setBroadcastMachine(find());
        return broadcastHomePage;
    }
}
