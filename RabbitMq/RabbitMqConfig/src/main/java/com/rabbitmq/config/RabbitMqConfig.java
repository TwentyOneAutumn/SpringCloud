package com.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RabbitMq配置类
 */
@Configuration
@ComponentScan(basePackages = {"com.rabbitmq"})
public class RabbitMqConfig {

    @Autowired
    private CustomConfirmCallback customConfirmCallback;

    @Autowired
    private CustomReturnCallback customReturnCallback;

    /**
     * 用Jackson2JsonMessageConverter覆盖系统默认消息转换器
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * RabbitTemplate
     * @param connectionFactory RabbitMQ连接工厂
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(customConfirmCallback);
        rabbitTemplate.setReturnsCallback(customReturnCallback);
        return rabbitTemplate;
    }
}

