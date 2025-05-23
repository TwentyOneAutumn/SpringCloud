package com.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistributedLockConfig2 {

    /**
     * Redisson Single模式
     */
    @Bean
    @ConditionalOnProperty(name = "spring.data.redis.host")
    public RedissonClient redissonClient(RedisProperties properties) {
        Config config = new Config();
        // 配置单机模式
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword());
        return Redisson.create(config);
    }
}
