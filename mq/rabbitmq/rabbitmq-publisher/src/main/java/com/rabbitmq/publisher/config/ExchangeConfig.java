package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.ExchangeEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机配置类
 */
@Configuration
public class ExchangeConfig {

    /**
     * 天气广播交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_FANOUT_EXCHANGE)
    public FanoutExchange weatherFanoutExchange() {
        return new FanoutExchange(ExchangeEnum.WEATHER_FANOUT_EXCHANGE,true,true);
    }

    /**
     * 天气直连交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_DIRECT_EXCHANGE)
    public DirectExchange weatherDirectExchange() {
        return new DirectExchange(ExchangeEnum.WEATHER_DIRECT_EXCHANGE,true,true);
    }

    /**
     * 天气主题交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_TOPIC_EXCHANGE)
    public TopicExchange weatherTopicExchange() {
        return new TopicExchange(ExchangeEnum.WEATHER_TOPIC_EXCHANGE,true,true);
    }


    /**
     * 天气头部交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_HEADERS_EXCHANGE)
    public HeadersExchange weatherHeadersExchange() {
        return new HeadersExchange(ExchangeEnum.WEATHER_HEADERS_EXCHANGE,true,true);
    }


    /**
     * 天气延迟交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_DELAY_EXCHANGE)
    public DirectExchange weatherDelayExchange() {
        return new DirectExchange(ExchangeEnum.WEATHER_DELAY_EXCHANGE,true,true);
    }


    /**
     * 天气死信交换机
     * @return Exchange
     */
    @Bean(ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE)
    public DirectExchange weatherDeadLetterExchange() {
        return new DirectExchange(ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE,true,true);
    }
}
