package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.enums.QueueEnum;
import com.rabbitmq.enums.RoutingKey;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 绑定 交换机 and 队列 配置类
 */
@Configuration
public class BindingConfig {

    @Bean
    public Binding weatherFanoutBinding(@Qualifier(ExchangeEnum.WEATHER_FANOUT_EXCHANGE) FanoutExchange exchange, @Qualifier(QueueEnum.WEATHER_FANOUT_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }


    @Bean
    public Binding weatherDirectBinding(@Qualifier(ExchangeEnum.WEATHER_DIRECT_EXCHANGE)DirectExchange exchange, @Qualifier(QueueEnum.WEATHER_DIRECT_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.WEATHER_KEY);
    }


    @Bean
    public Binding weatherTopicBinding(@Qualifier(ExchangeEnum.WEATHER_TOPIC_EXCHANGE)TopicExchange exchange, @Qualifier(QueueEnum.WEATHER_TOPIC_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.WEATHER_KEY);
    }


    @Bean
    public Binding weatherHeadersBinding(@Qualifier(ExchangeEnum.WEATHER_HEADERS_EXCHANGE)HeadersExchange exchange, @Qualifier(QueueEnum.WEATHER_HEADERS_QUEUE) Queue queue){
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("weatherHeader","context");
        return BindingBuilder.bind(queue).to(exchange).whereAll(headers).match();
    }

    @Bean
    public Binding weatherDelayBinding(@Qualifier(ExchangeEnum.WEATHER_DELAY_EXCHANGE)DirectExchange exchange, @Qualifier(QueueEnum.WEATHER_DELAY_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.WEATHER_KEY);
    }

    @Bean
    public Binding weatherDeadLetterBinding(@Qualifier(ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE)DirectExchange exchange, @Qualifier(QueueEnum.WEATHER_DEAD_LETTER_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RoutingKey.DEAD_LETTER_KEY);
    }

    @Bean
    public Binding weatherManualAckBinding(@Qualifier(ExchangeEnum.WEATHER_FANOUT_EXCHANGE) FanoutExchange exchange, @Qualifier(QueueEnum.WEATHER_MANUAL_ACK_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
