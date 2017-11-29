package com.iqes.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 54312
 *
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    private  Connection connection;
    private  Channel channel;

    public EmitLog() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
    }

    public  void send(String message) throws Exception {
        System.out.println("生产者发送数据！！！！");

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.basicPublish(EXCHANGE_NAME, "update_restaurant_info", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}

