package com.iqes.web.restaurant;

import com.iqes.service.restaurant.TableNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "restaurant/tableNumber")
public class TableNumberController {

    @Autowired
    private TableNumberService tableNumberService;
}
