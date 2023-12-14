package com.rabbitmq.publisher.config;

import com.rabbitmq.enums.DelayEnum;
import com.rabbitmq.enums.ExchangeEnum;
import com.rabbitmq.enums.QueueEnum;
import com.rabbitmq.enums.RoutingKey;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 队列配置类
 */
@Configuration
public class QueueConfig {

    /**
     * 天气广播队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_FANOUT_QUEUE)
    public Queue weatherFanoutQueue() {
        return new Queue(QueueEnum.WEATHER_FANOUT_QUEUE,true,false,false);
    }

    /**
     * 天气直连队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_DIRECT_QUEUE)
    public Queue weatherDirectQueue() {
        return new Queue(QueueEnum.WEATHER_DIRECT_QUEUE,true,false,false);
    }

    /**
     * 天气主题队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_TOPIC_QUEUE)
    public Queue weatherTopicQueue() {
        return new Queue(QueueEnum.WEATHER_TOPIC_QUEUE,true,false,false);
    }


    /**
     * 天气头队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_HEADERS_QUEUE)
    public Queue weatherHeadersQueue() {
        return new Queue(QueueEnum.WEATHER_HEADERS_QUEUE,true,false,false);
    }


    /**
     * 天气延迟队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_DELAY_QUEUE)
    public Queue weatherDelayQueue() {
        HashMap<String, Object> args = new HashMap<>();
        // 声明队列消息过期时间
        args.put(DelayEnum.TTL,300L);
        // 设置当前队列绑定的死信交换机
        args.put(DelayEnum.DEAD_LETTER_EXCHANGE, ExchangeEnum.WEATHER_DEAD_LETTER_EXCHANGE);
        // 设置当前队列绑定的死信交换机所对应的RoutingKey
        args.put(DelayEnum.DEAD_LETTER_ROUTING_KEY, RoutingKey.DEAD_LETTER_KEY);
        return new Queue(QueueEnum.WEATHER_DELAY_QUEUE,true,false,false,args);
    }


    /**
     * 天气死信队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_DEAD_LETTER_QUEUE)
    public Queue weatherDeadLetterQueue() {
        return new Queue(QueueEnum.WEATHER_DEAD_LETTER_QUEUE,true,false,false);
    }


    /**
     * 天气手动ACK队列
     * @return Queue
     */
    @Bean(QueueEnum.WEATHER_MANUAL_ACK_QUEUE)
    public Queue weatherManualAckQueue() {
        return new Queue(QueueEnum.WEATHER_MANUAL_ACK_QUEUE,true,false,false);
    }

}
