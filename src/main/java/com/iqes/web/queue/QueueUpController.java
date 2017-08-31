package com.iqes.web.queue;

import com.iqes.service.queue.QueueQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * 顾客虚拟入队并确认入队
 *
 * @author jiangdongdong.tp
 *
 */
@Controller
@RequestMapping(value ="queue")
public class QueueUpController {

    @Autowired
    private QueueQueryService queueQueryService;


    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String virtualQueue(){
        return "";
    }
}
