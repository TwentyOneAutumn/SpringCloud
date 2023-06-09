package com.rabbitmq.publisher.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfig {

    @Bean
    public Binding weatherBinding(@Qualifier("weatherExchange")Exchange weatherExchange, @Qualifier("weatherQueue") Queue weatherQueue){
        return BindingBuilder.bind(weatherQueue).to(weatherExchange).with("weather").noargs();
    }

}
