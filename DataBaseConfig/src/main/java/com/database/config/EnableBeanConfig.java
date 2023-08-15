package com.database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnableBeanConfig {

    /**
     * MybatisPlus表主键生成器
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator();
    }

    /**
     * RedisTemplate
     * @param factory Redis连接工厂
     * @return RedisTemplate
     */
//    @Bean
//    public RedisTemplate<String, Object> redisClient(RedisConnectionFactory factory) {
//        // 创建RedisTemplate<String, Object>对象
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        // 配置连接工厂
//        redisTemplate.setConnectionFactory(factory);
//        // 定义Jackson2JsonRedisSerializer序列化对象
//        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异常
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        jacksonSerializer.setObjectMapper(objectMapper);
//        StringRedisSerializer stringSerial = new StringRedisSerializer();
//        // redis key 序列化方式使用stringSerial
//        redisTemplate.setKeySerializer(stringSerial);
//        // redis value 序列化方式使用jackson
//        redisTemplate.setValueSerializer(jacksonSerializer);
//        // redis hash key 序列化方式使用stringSerial
//        redisTemplate.setHashKeySerializer(stringSerial);
//        // redis hash value 序列化方式使用jackson
//        redisTemplate.setHashValueSerializer(jacksonSerializer);
//        // 初始化
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
}
