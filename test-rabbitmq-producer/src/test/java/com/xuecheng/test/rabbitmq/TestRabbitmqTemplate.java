package com.xuecheng.test.rabbitmq;

import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname TestRabbitmqTemplate
 * @Description TODO
 * @Date 2019/7/8 10:00
 * @Created by Jiavg
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRabbitmqTemplate {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    private String message = "send Email to User...";
    
    @Test
    public void testTemplate(){
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.email", message);
    }
    
}
