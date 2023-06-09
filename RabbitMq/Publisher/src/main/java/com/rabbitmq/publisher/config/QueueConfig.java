package com.rabbitmq.publisher.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    /**
     * 天气队列
     * @return Exchange
     */
    @Bean("weatherQueue")
    public Queue weatherQueue() {
        return new Queue("weatherQueue",true,false,false);
    }
}
