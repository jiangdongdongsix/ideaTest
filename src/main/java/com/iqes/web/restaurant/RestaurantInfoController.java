package com.iqes.web.restaurant;

import com.iqes.service.restaurant.RestaurantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "restaurant/restaurantInfo")
public class RestaurantInfoController {

    @Autowired
    private RestaurantInfoService restaurantInfoService;
}
