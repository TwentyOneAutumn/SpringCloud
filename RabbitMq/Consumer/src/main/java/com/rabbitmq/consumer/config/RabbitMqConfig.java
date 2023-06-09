package com.rabbitmq.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq配置类
 * 用于声明交换机和队列并使其绑定
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 声明队列 : WorkQueue
     * @return Queue
     */
    @Bean
    public Queue WorkQueue(){
        return new Queue("Work.Queue");
    }

    /**
     * 用Jackson2JsonMessageConverter覆盖系统默认消息转换器
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter messageConverter(){
       return new Jackson2JsonMessageConverter();
    }

}
