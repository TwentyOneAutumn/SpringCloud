package com.app.redis.lock;

import com.redis.interfaces.RedisMessageHandler;
import com.redis.interfaces.RedisMessageListener;
import com.redis.lock.RedisDistributedLock;
import com.redis.topic.RedisTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
public class RedisConfig {

    /**
     * 配置Redis锁对象
     */
    @Bean
    public RedisDistributedLock appLock(RedisTemplate<String,Object> redisTemplate) {
        return new RedisDistributedLock(redisTemplate,"appLock",5);
    }


    /**
     * 消息通知处理器
     */
    @Bean
    public RedisMessageHandler redisHandler1(RedisTemplate<String,Object> redisTemplate){
        return new TestHandler("A",redisTemplate);
    }

    /**
     * 消息通知监听器
     */
    @Bean
    public RedisMessageListener redisMessageListener1(RedisMessageHandler redisHandler1){
        return new RedisMessageListener(redisHandler1, RedisTopic.INFORM);
    }


    /**
     * 消息通知处理器
     */
    @Bean
    public RedisMessageHandler redisHandler2(RedisTemplate<String,Object> redisTemplate){
        return new TestHandler("B",redisTemplate);
    }

    /**
     * 消息通知监听器
     */
    @Bean
    public RedisMessageListener redisMessageListener2(RedisMessageHandler redisHandler2){
        return new RedisMessageListener(redisHandler2, RedisTopic.INFORM);
    }


    /**
     * 消息通知处理器
     */
    @Bean
    public RedisMessageHandler redisHandler3(RedisTemplate<String,Object> redisTemplate){
        return new TestHandler("C",redisTemplate);
    }

    /**
     * 消息通知监听器
     */
    @Bean
    public RedisMessageListener redisMessageListener3(RedisMessageHandler redisHandler3){
        return new RedisMessageListener(redisHandler3, RedisTopic.INFORM);
    }
}
