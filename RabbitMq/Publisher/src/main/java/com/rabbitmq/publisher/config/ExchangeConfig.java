package com.rabbitmq.publisher.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    /**
     * 天气交换机
     * @return Exchange
     */
    @Bean("weatherExchange")
    public Exchange weatherExchange() {
        return new TopicExchange("weatherExchange",true,true);
    }

}
