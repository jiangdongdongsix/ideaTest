package com.iqes.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 54312
 * 分发请求命令到具体方法
 */
@Component
public class Dispatcher {

    @Autowired
    private AppShowService appShowService;

    @Autowired
    private QueueService queueService;


    public  JSONObject dispatcherCommand(String jsonString){

        RabbitCarrier rabbitCarrier= JSONObject.parseObject(jsonString,RabbitCarrier.class);
        System.out.println("______________________________________");
        System.out.println("载体携带的信息： "+rabbitCarrier);
        System.out.println(rabbitCarrier.getServiceName()+"----"+rabbitCarrier.getMethodName());
        System.out.println("______________________________________");

        String serviceName=rabbitCarrier.getServiceName();
        String methodName=rabbitCarrier.getMethodName();
        String data=rabbitCarrier.getParameter();

        switch (serviceName){
            case "APPShowService":
                switch (methodName){
                    case "checkRestaurantInfoAndAllQueueInfo":return appShowService.checkRestaurantInfoAndAllQueueInfo();
                    case "getMenus":return appShowService.getMenus();
                    default:
                        System.out.println("method-no-matching");
                }
                break;
            case "QueueService":
                switch (methodName){
                    case "virtualQueue":return queueService.virtualQueue(data);
                    case "updateVirtual":return queueService.updateVirtual(data);
                    case "confirmQueue":return queueService.confirmQueue(data);
                    case "cancleQueue":return queueService.cancelQueue(data);
                    case "checkPersonQueue":return queueService.checkPersonalQueueInfo(data);
                    case "checkAllQueue" :return queueService.checkAllQueueInfo();
                    default:
                        System.out.println("method-no-matching");
                }
           default:
                System.out.println("service-no-matching");
        }
        return null;
    }
}
