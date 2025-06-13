package com.redis.config;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.redis.channel.RedisChannel;
import com.redis.channel.RedisMessageListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class RedisConfig {

    /**
     * RedisSentinel连接工厂
     */
    @Bean
    @ConditionalOnProperty("spring.redis.sentinel.nodes")
    public RedisConnectionFactory redisConnectionFactory(RedisProperties properties) {
        // 创建Sentinel配置对象
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        // 获取Sentinel配置
        RedisProperties.Sentinel sentinel = properties.getSentinel();
        // 设置Redis密码
        configuration.setPassword(sentinel.getPassword());
        // 设置哨兵认证密码
        configuration.setSentinelPassword(sentinel.getPassword());
        // 转换RedisSentinelNode
        Set<RedisNode> nodes = sentinel.getNodes().stream().map(address -> new RedisNode(address.split(":")[0], Integer.parseInt(address.split(":")[1]))).collect(Collectors.toSet());
        // 设置RedisSentinelNode
        configuration.setSentinels(nodes);
        // 设置Master节点名称
        configuration.setMaster(sentinel.getMaster());
        return new LettuceConnectionFactory(configuration);
    }




    /**
     * 配置 Redis 消息监听器容器
     * @param redisConnectionFactory Redis连接工厂
     * @param listeners 监听器集合
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, List<RedisMessageListener> listeners) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置连接工厂
        container.setConnectionFactory(redisConnectionFactory);
        if(CollUtil.isNotEmpty(listeners)){
            listeners.forEach(listener -> {
                // 添加消息监听器和频道
                container.addMessageListener(listener.getListener(), listener.getTopic());
                container.afterPropertiesSet();
            });
        }
        return container;
    }


    /**
     * Redis消息推送类
     */
    @Bean
    public RedisChannel redisChannel(RedisTemplate<String, Object> redisTemplate){
        return new RedisChannel(redisTemplate);
    }
}
