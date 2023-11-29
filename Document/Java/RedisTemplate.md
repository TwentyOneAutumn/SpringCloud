# RedisTemplate

## 配置

------

### Maven依赖

```xml
<!-- SpringBootRedis依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- jackson依赖 -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

### application.yml

```yaml
spring:
  redis:
    host: Redis地址(default localhost)
    port: 端口号(default 6379)
    password: 密码((default null)
```

### 配置RedisTemplate

```java
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate配置类
 */
@Configuration
public class RedisConfig {

    /**
     * RedisTemplate
     * @param factory Redis连接工厂对象
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisClient(RedisConnectionFactory factory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 定义Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
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
}
```



## RedisTemplate<K,V>

| 方法                                            | 返回值               | 说明                                                         |
| ----------------------------------------------- | -------------------- | ------------------------------------------------------------ |
| opsForValue()                                   | ValueOperations<K,V> | 获取String操作对象                                           |
| opsForList()                                    | ListOperations<K,V>  | 获取List操作对象                                             |
| opsForSet()                                     | SetOperations<K,V>   | 获取Set操作对象                                              |
| opsForHash()                                    | HashOperations<K,V>  | 获取Hash操作对象                                             |
| delete(K key)                                   | Boolean              | 移除指定的Key                                                |
| delete(Collection<K> keys)                      | Long                 | 移除指定的一组Key                                            |
| copy(K sourceKey, K targetKey, boolean replace) | Boolean              | 将`源键(sourceKey)`的值复制到`目标键(targetKey)`,replace(是否覆盖目标键的值) |
| hasKey(K key)                                   | Boolean              | 判断指定的Key是否存在                                        |
| type(K key)                                     | DataType             | 判断指定的Key的类型                                          |
| keys(K pattern)                                 | Set<K>               | 查找与指定表达式匹配的所有Key                                |
| rename(K oldKey, K newKey)                      | void                 | 将`oldKey`重命名为`newKey`                                   |
| renameIfAbsent(K oldKey, K newKey)              | Boolean              | 当`newKey`不存在时，才将`oldKey`重命名为`newKey`             |
| expire(K key, long timeout, TimeUnit unit)      | Boolean              | 设置目标`Key`的`TTL`                                         |
| persist(K key)                                  | Boolean              | 移除目标Key的TTL,使其永不过期                                |



### 方法

| 方法 | 说明 |
| ---- | ---- |
|      |      |
|      |      |
|      |      |

