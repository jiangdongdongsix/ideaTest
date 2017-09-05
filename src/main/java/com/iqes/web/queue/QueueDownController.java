package com.iqes.web.queue;

import com.iqes.entity.QueueInfo;
import com.iqes.service.queue.ExtractNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "queue")
public class QueueDownController {

    @Autowired
    private ExtractNumberService extractNumberService;

    @RequestMapping(value = "extractNumber",method = RequestMethod.GET)
    public QueueInfo extractNumber(@RequestParam("tableName")String tableName){
        return extractNumberService.ExtractNumber(tableName);
    }

    @RequestMapping(value = "queryNumber",method = RequestMethod.GET)
    public QueueInfo queryNumber(){

        return null;
    }
}
