package com.iqes.web.restaurant;

import com.iqes.service.restaurant.ConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "restaurant/configInfo")
public class ConfigInfoController {

    @Autowired
    private ConfigInfoService configInfoService;

}
