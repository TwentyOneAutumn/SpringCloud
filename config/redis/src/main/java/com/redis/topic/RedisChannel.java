package com.redis.topic;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

public class RedisChannel {

    /**
     * RedisTemplate对象
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 静态引用变量
     */
    private static RedisTemplate<String, Object> staticRedisTemplate;


    @PostConstruct
    public void init() {
        staticRedisTemplate = redisTemplate;
    }

    /**
     * 构造方法
     * @param redisTemplate
     */
    public RedisChannel(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    /**
     * 推送消息
     * @param channel 主题
     * @param message 消息
     */
    public static void push(String channel, String message){
        staticRedisTemplate.convertAndSend(channel,message);
    }
}
