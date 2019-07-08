package com.xuecheng.test.rabbitmq.mq;

import com.rabbitmq.client.Channel;
import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Classname ReserveHandle
 * @Description TODO
 * @Date 2019/7/8 10:30
 * @Created by Jiavg
 */
@Component
public class ReserveHandle {
    
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL })
    public void reserve_Email(String msg, Message message, Channel channel){
        System.out.printf(msg);
    }
    
}
