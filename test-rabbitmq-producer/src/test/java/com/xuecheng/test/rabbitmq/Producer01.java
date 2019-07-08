package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.sql.Date;
import java.util.concurrent.TimeoutException;

/**
 * @Classname Producer01
 * @Description TODO
 * @Date 2019/7/4 10:55
 * @Created by Jiavg
 */
public class Producer01 {

    private static final String QUEUE = "Helloworld";
    
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = null;
        Channel channel = null;

                ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");

        try {
            // 创建与RabbitMQ服务的TCP连接
            connection = factory.newConnection();
            // 创建与ExChange的通道,每个连接可以创建多个通道,每个通道代表一个会话任务
            channel = connection.createChannel();
            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * param1:队列名称
             * param2:是否持久化
             * param3:队列是否独占此连接
             * param4:队列不再使用时是否自动删除此队列
             * param5:队列参数
             */
            channel.queueDeclare(QUEUE, true, false, false, null);
            
            String message = "Hello Jiavg" + new Date(System.currentTimeMillis()).toLocalDate();

            channel.basicPublish("", QUEUE, null, message.getBytes());
            System.out.printf("传输完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(channel != null)
            {
                channel.close();
            }
            if(connection != null)
            {
                connection.close();
            }
        }

    }
    
}
