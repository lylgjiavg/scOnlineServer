package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Classname Consumer01
 * @Description TODO
 * @Date 2019/7/4 15:10
 * @Created by Jiavg
 */
public class Consumer01 {

    private static final String QUEUE = "Helloworld";
    
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        /**
         * 声明队列，如果Rabbit中没有此队列将自动创建
         * param1:队列名称
         * param2:是否持久化
         * param3:队列是否独占此连接
         * param4:队列不再使用时是否自动删除此队列
         * param5:队列参数
         */
        channel.queueDeclare(QUEUE, true, false, false, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /**
             * 消费者接收消息调用此方法
             * @param consumerTag  消费者的标签，在channel.basicConsume()去指定
             * @param envelope  消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
             * @param properties
             * @param body
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.printf("Reserve message:" + new String(body, "utf-8"));
            }
        };
        
        channel.basicConsume(QUEUE, true, defaultConsumer);
        
    }
    
}
