package com.iqes.web.restaurant;

import com.iqes.service.restaurant.RestaurantPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "restaurant/restaurantPhoto")
public class RestaurantPhotoController {

    @Autowired
    private RestaurantPhotoService restaurantPhotoService;
}
