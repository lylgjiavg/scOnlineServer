package com.xuecheng.test.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RabbitmqConfig
 * @Description TODO
 * @Date 2019/7/8 9:02
 * @Created by Jiavg
 */
@Configuration
public class RabbitmqConfig {
    
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";
    public static final String ROUTINGKEY_EMAIL="inform.#.email.#";
    public static final String ROUTINGKEY_SMS="inform.#.sms.#";
    
    // 声明交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }
    
    // 声明队列
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        return new Queue(QUEUE_INFORM_EMAIL);
    }

    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS(){
        return new Queue(QUEUE_INFORM_SMS);
    }
    
    // 绑定队列到交换机
    @Bean
    public Binding Binding_ROUTINGKEY_EMAIL(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange, 
                                            @Qualifier(QUEUE_INFORM_EMAIL) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
    }
    
    @Bean
    public Binding Binding_ROUTINGKEY_SMS(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange, 
                                            @Qualifier(QUEUE_INFORM_SMS) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
    }
    
}
