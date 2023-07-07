# Gateway

##### API网关

什么是API网关？

API网关作用就是把各个服务对外提供的API汇聚起来，让外界看起来是一个统一的接口，同时也可以在网关中提供额外的功能

总结 ：网关就是所有项目的一个统一的入口，所以访问项目的请求都要先经过网关，由网关路由到各个服务



##### Gateway依赖

```xml
 <!-- Gateway网关依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
    <version>3.1.1</version>
</dependency>

<!-- loadbalancer负载均衡依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-loadbalancer</artifactId>
    <version>3.1.1</version>
</dependency>

<!-- Nacos注册发现依赖 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

> **注意：Gateway网关与SpringBoot-web依赖冲突，请在Gateway项目中干掉此依赖**



### 请求限流控制过滤器

##### RequestRateLimiterGatewayFilterFactory

基于令牌桶算法实现限流



RequestRateLimiterGatewayFilterFactory.config配置

```java
public static class Config implements HasRouteId {

    // 令牌算法
	private KeyResolver keyResolver;

    // 速度限制器
	private RateLimiter rateLimiter;
}
```

> **RequestRateLimiter需要两个配置**
>
> ​	**KeyResolver：无实现类，需要自己实现**
>
> ​	**RateLimiter：有实现类RedisRateLimiter，将Redis作为桶**



##### RedisRateLimiter

```java
@ConfigurationProperties("spring.cloud.gateway.redis-rate-limiter")
public class RedisRateLimiter extends AbstractRateLimiter<RedisRateLimiter.Config> implements ApplicationContextAware {

	// 每秒钟生产的令牌数量
	public static final String CONFIGURATION_PROPERTY_NAME = "redis-rate-limiter";

    // 令牌桶的容量上限
	public static final String REDIS_SCRIPT_NAME = "redisRequestRateLimiterScript";
}
```

