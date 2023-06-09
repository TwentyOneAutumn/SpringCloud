package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.enums.QueueEnum;
import com.rabbitmq.enums.RoutingKey;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 绑定 交换机 and 队列 配置类
 */
@Configuration
public class BindingConfig {

    @Bean
    public Binding weatherBinding(@Qualifier(ExchangeEnum.WEATHER_TOPIC_EXCHANGE)Exchange weatherExchange, @Qualifier(QueueEnum.WEATHER_QUEUE) Queue weatherQueue){
        return BindingBuilder.bind(weatherQueue).to(weatherExchange).with(RoutingKey.WEATHER_KEY).noargs();
    }
}
