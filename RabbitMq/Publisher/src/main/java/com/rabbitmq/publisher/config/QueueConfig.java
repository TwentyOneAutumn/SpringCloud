package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.QueueEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置类
 */
@Configuration
public class QueueConfig {

    /**
     * 天气队列
     * @return Exchange
     */
    @Bean(QueueEnum.WEATHER_QUEUE)
    public Queue weatherQueue() {
        return new Queue(QueueEnum.WEATHER_QUEUE,true,false,false);
    }
}
