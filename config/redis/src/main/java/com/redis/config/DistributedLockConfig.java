package com.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.redis.aspects.DistributedLockAspect2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class DistributedLockConfig {

    /**
     * Redisson Single模式
     */
    @Bean
    public RedissonClient redissonClient(RedisProperties properties) {
        Config config = new Config();
        // 配置单机模式
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort());
//                .setPassword(properties.getPassword());
        return Redisson.create(config);
    }


    /**
     * Redisson Sentinel模式
     */
//    @Bean
//    @ConditionalOnProperty(name = "spring.data.redis.sentinel.nodes")
//    public RedissonClient redissonSentinelClient(RedisProperties properties) {
//        RedisProperties.Sentinel sentinel = properties.getSentinel();
//        Config config = new Config();
//        // 配置Sentinel模式
//        config.useSentinelServers()
//                .setMasterName(sentinel.getMaster())
//                .addSentinelAddress(sentinel.getNodes().stream().map(item -> "redis://" + item).toArray(String[]::new))
//                .setPassword(properties.getPassword())
//                .setSentinelPassword(sentinel.getPassword())
//                .setCheckSentinelsList(true)
//                .setReadMode(org.redisson.config.ReadMode.MASTER_SLAVE)
//                .setSubscriptionMode(org.redisson.config.SubscriptionMode.MASTER);
//        return Redisson.create(config);
//    }


    /**
     * RedisTemplate配置
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(factory);

        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 定义Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jacksonSerializer.setObjectMapper(objectMapper);
        StringRedisSerializer stringSerial = new StringRedisSerializer();
        // 设置RedisKey的列化方式:StringRedisSerializer
        redisTemplate.setKeySerializer(stringSerial);
        // 设置RedisValue的列化方式:Jackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(jacksonSerializer);
        // 初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    @Bean
    public DistributedLockAspect2 singleDistributedLockAspect(RedissonClient redissonClient, RedisTemplate<String, String> redisTemplate) {
        return new DistributedLockAspect2(redissonClient,redisTemplate);
    }

//    @Bean
//    @ConditionalOnProperty(name = "spring.data.redis.sentinel.nodes")
//    public DistributedLockAspect sentinelDistributedLockAspect(RedissonClient redissonSentinelClient) {
//        return new DistributedLockAspect(redissonSentinelClient);
//    }
}
