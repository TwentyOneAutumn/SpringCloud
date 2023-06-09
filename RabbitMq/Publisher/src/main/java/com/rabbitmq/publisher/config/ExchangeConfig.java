package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机配置类
 */
@Configuration
public class ExchangeConfig {

    /**
     * 天气交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_TOPIC_EXCHANGE)
    public Exchange weatherExchange() {
        return new TopicExchange(ExchangeEnum.WEATHER_TOPIC_EXCHANGE,true,true);
    }

}
