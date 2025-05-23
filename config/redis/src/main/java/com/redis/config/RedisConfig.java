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
     * RedisTemplate配置
     */
    @Bean
    public RedisTemplate<String, Long> redisTemplate(RedisConnectionFactory factory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
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
