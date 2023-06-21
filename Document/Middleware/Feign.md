# Feign



---



### Feign简介

Feign的作用是方便简化服务之间的远程调用和数据交互

默认集成了Ribbon负载均衡

Feign具有扩展功能，支持OKHttp，Gzip



---



### SpringCloud整合Feign依赖

```xml
<!-- OpenFeign依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>3.1.3</version>
</dependency>

```



---



### Feign相关注解



| 注解               | 作用                                                         | 属性         | 属性值                  |
| ------------------ | ------------------------------------------------------------ | ------------ | ----------------------- |
| @EnbleFeignClients | 放在启动类上，表示扫描启动类所在包以及子包中所有的FeignClient客户端，也可以通过属性来指定扫描 | basePackages | "扫描包中的Fegin客户端" |
| @FeignClient       | 标识一个类为FeignClient客户端                                | value        | 服务名称                |
|                    |                                                              | contextId    | Bean名称                |
|                    |                                                              | path         | 客户端路径前缀          |



---



### Feign使用



##### 开启Fegin

```java
@EnableFeignClients(basePackages = "com.demo.feign")
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
```



##### 定义Cotroller

```java
@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    UserService userService;

    /**
     * 根据ID查询用户信息List
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PostMapping("/list")
    AjaxResult toList(@RequestBody String userId){
        return userService.toList(userId);
    }
}
```



##### 定义Feign客户端

```java
@FeignClient(value = "User" , path = "/user" , contextId = "adminUserFeign")
public class AdminUserFeign {

    @PostMapping("/admin/list")
    AjaxResult toList(@RequestBody String userId){
        return userService.toList(userId);
    }
}
```



##### 调用Feign

```java
@RestController
@RequestMapping("/login")
public class LoginUserController {
    
    @Autowired
    AdminUserFeign adminUserFeign;

    /**
     * 根据ID查询用户信息List
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/list")
    AjaxResult toList(){
        
        return adminUserFeign.toList();
    }
}
```



> PS：如果Feign方法有多个参数，就会发生Erorr：Method has too many Body parameters，此时需要在Feign端的参数前加上@RequestParam注解，就可以解决问题了
>



---



### Feign日志



##### 日志级别

| 类型    | 作用                                      |
| ------- | ----------------------------------------- |
| NONE    | 不记录任何日志（默认）                    |
| BASIC   | 仅记录请求方法，URL，响应状态码，执行时间 |
| HEADERS | 记录BASIC级别的基础上，记录请求头和响应头 |
| FULL    | 记录请求的所有数据                        |



##### 基于配置文件的方式配置

```yaml
feign:
  client:
    config:
      default:  # default:全局配置  服务名称:针对某个服务配置
        loggerLevel: NONE # NONE,BASIC,HEADERS,FULL
```



##### 基于配置Bean的方式配置

```java
/**
 * Feign配置类
 */
@Configuration
public class FeignClientConfiguration {
    /**
     * 配置Feign日志级别
     * @return Logger.Level
     */
    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.NONE;
    }
}
```



---



### 配置Feign降级规则



##### 开启Feign的Sentinel支持

```yaml
feign:
 sentinel:
  enable: true
```



##### 给FeignClient编写降级工厂类

```java
public class UserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        return new RemoteUserService(){

            @Override
            public Row<UserInfo> getUserInfo(SysUser user) {
                return Build.buildRow(false,"调用基础模块getUserInfo接口异常");
            }

            @Override
            public Row<Boolean> checkUser(SysUser user) {
                return Build.buildRow(false,"调用基础模块checkUser接口异常");
            }
        };
    }
}
```



##### 给对应的FeignClient设置降级工厂

```java
@FeignClient(contextId = "RemoteUserService",value = ServiceName.BASIC,path = "basic",fallbackFactory = UserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 根据用户账号获取用户信息
     * @param user 数据对象
     * @return UserInfo
     */
    @PostMapping("/user/info")
    Row<UserInfo> getUserInfo(@RequestBody SysUser user);


    /**
     * 判断当前用户是否存在
     * @param user 数据对象
     * @return Boolean
     */
    @PostMapping("/user/check")
    Row<Boolean> checkUser(@RequestBody SysUser user);
}
```

