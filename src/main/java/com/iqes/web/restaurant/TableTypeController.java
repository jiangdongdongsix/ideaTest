package com.iqes.web.restaurant;

import com.iqes.service.restaurant.TableTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "restaurant/tableType")
public class TableTypeController {

    @Autowired
    private TableTypeService tableTypeService;
}
