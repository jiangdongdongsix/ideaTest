package com.iqes.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Lazy(value = false)
@Component
public class RPCServer  {

    private static final String RPC_QUEUE_NAME = "restaurant_1_rpc_queue";

    @Autowired
    private Dispatcher dispatcher;

    private Connection connection;

    @PostConstruct
    public void waitingRequest() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
      //  factory.setUsername("testUser");
       // factory.setPassword("123");

        try {
            connection      = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();

                    String response = "";
                    JSONObject jsonObject=null;

                    try {
                        String message = new String(body,"UTF-8");
                        jsonObject=dispatcher.dispatcherCommand(message);
                        response=JSONObject.toJSONString(jsonObject, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        System.out.println(" [.] (" + response + ")");
                    }
                    catch (Exception e){
                        System.out.println(" [.],this " + e.toString());
                    }  finally {
                        channel.basicPublish( "", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
                        channel.basicAck(envelope.getDeliveryTag(), false);
                        // RabbitMq consumer worker thread notifies the RPC server owner thread
                        synchronized(this) {
                            this.notify();
                        }
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
//            // Wait and be prepared to consume the message from RPC client.
//            while (true) {
//                synchronized(consumer) {
//                    try {
//                        consumer.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
