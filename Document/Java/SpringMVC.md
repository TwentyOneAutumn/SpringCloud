# SpringMVC重点知识



-----



### MVC架构

| MVC名称    | 说明   |
| ---------- | ------ |
| Model      | 模型层 |
| view       | 视图层 |
| Controller | 控制层 |



-----



### SpringMVC执行流程

![SpringMVC执行流程图](.\img\SpringMVC执行流程图.png)



-----



### SpringMVC组件

| 组件                          | 说明                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| DispatcherServlet(前端控制器) | 相当于MVC模式中的C，是用来控制流程的中心组件，调用其他组件处理用户的请求，降低了组件之间的耦合性 |
| HandlerMapping(处理器映射器)  | 负责根据用户的请求Url找到Handler                             |
| Handler(处理器)               | 自己定义的Controller处理单元，具体对用户的请求进行处理并返回结果 |
| HandlerAdapter(处理器适配器)  | 处理用户请求携带的数据，封装并交给Handler，将Handler返回的数据封装为ModelAndView对象，并返回给DispatcherServlet |
| ViewResolver(视图解析器)      | 负责将处理结果解析，并生成View，解析View地址等，并将解析后的View返回 |



-----



### 接收参数

| 方式       | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| 正常类型   | 可以自动转换的数据会自动化转换                               |
| POJO实体类 | 要求参数名称必须和实体类属性名一致，底层通过Set方法进行赋值  |
| Date类型   | 日期类型的参数或属性前可以加上@DateTimeFormat注解，可以通过其pattern属性指定日期格式，也可以定义类型转换器(实现Converter接口)，就可以完成自动转换 |
| List       | 数据的必须是Json格式                                         |
| Map        | 接收的数据的必须是Json格式                                   |



-----



### 自定义类型转换器

```java
package com.demo.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class beanConfiguration {


    /**
     * 自定义类型转换器:String to Date
     * @return 类型转换器
     */
    @Bean
    public Converter<String, Date> StringToDateConverter(){
        return new Converter<String, Date>() {
            @Override
            public Date convert(String dateString) {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd HH-ss-mm").parse(dateString);
                } catch (Exception e) {
                    throw  new RuntimeException("日期转换异常");
                }
            }
        };
    }
}
```



-----



### 视图解析器

```java
package com.demo.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class orderBeanConfiguration {


    /**
     * 配置视图解析器
     * @return 视图解析器
     */
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        // 配置视图前缀
        internalResourceViewResolver.setPrefix("");
        // 配置视图后缀
        internalResourceViewResolver.setSuffix("");
        // 配置编码格式
        internalResourceViewResolver.setContentType("UTF-8");
        // 配置是否转发
        internalResourceViewResolver.setAlwaysInclude(false);
        return internalResourceViewResolver;
    }
}
```



-----



### 转发和重定向

| 功能             | 用法               | 说明                                                         |
| ---------------- | ------------------ | ------------------------------------------------------------ |
| forward(转发)    | forward:/路径地址  | forward是服务器内部的跳转，浏览器的地址栏不会发生变化，从一个页面到另一个页面的跳转还是同一个请求，也即是只有一个请求响应。可以通过request域来传递数据 |
| redirect(重定向) | redirect:/路径地址 | redirect是浏览器自动发起对跳转目标的请求，浏览器的地址栏会发生变化。从一个页面到另一个页面的跳转是不同的请求，也即是有两个或两个以上的不同的请求的响应，无法通过request域来传递数据 |



-----



### 拦截器



##### 拦截器说明

SpringMVC的拦截器(Interceptor)类似于Servlet中的过滤器(Filter)，主要用于拦截用户请求并作出相应处理，例如进行权限验证，记录请求信息的日志，判断用户是否登录等



##### 拦截器定义

1. 通过实现HandlerInterceptor接口，或继承其实现类来定义
2. 通过实现WebRequestInterceptor接口，或继承其实现类来定义

```java
package com.demo.order.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
@Component
public class orderInterceptor implements HandlerInterceptor {

    /**
     * 在请求到达handler之前进行拦截
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @return true:放行 false:拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置请求和响应的编码格式
        // 请求权限控制
        // 记录请求日志
        return true;
    }

    /**
     * handler返回ModelAndView的时候进行拦截
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @param modelAndView 处理器返回的ModelAndView对象
     * @throws Exception 异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 对ModelAndView进行处理
    }

    /**
     * 在前端渲染View完毕，返回的时候拦截
     * 无论是否出现异常都会执行
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器
     * @param ex 异常
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 资源释放，处理异常
    }
}
```

```java
package com.demo.order.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器注册顺序
 */
@Component
public class orderWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    orderInterceptor orderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1.加入的顺序就是拦截器执行的顺序，
        //2.按顺序执行所有拦截器的preHandle
        //3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle
        registry.addInterceptor(orderInterceptor);
    }
}
```





##### 拦截器和过滤器的区别

| 拦截器                                                       | 过滤器                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| 拦截器依赖于Spring容器，需Spring容器来完成拦截器的初始化等等操作 | 过滤器依赖Tomcat环境中的Servlet容器，由Servlet容器完成过滤器的初始化等等操作 |
| 拦截器的工作范围是在前端控制后，所以只能对action起作用，无法拦截例如前端访问静态资源的请求，因为请求不经过前端控制器，所以拦截器无法进行拦截处理 | 过滤器可以对访问后台的任何请求进行过滤，包括静态资源访问等请求 |
| 拦截器可以访问请求的上下文信息                               | 过滤器无法访问请求的上下文信息                               |
| 拦截器在action的生命周期中，可以被多次调用                   | 过滤器在action的生命周期中，只能在容器初始化的时候被调用一次 |
| 拦截器可以访问Spring容器中的Bean                             | 过滤器访问Spring容器中的Bean很繁琐，不太方便                 |



-----



### 异常处理器



##### 基于Controller定义异常处理器

```java
@ExceptionHandler
public ModelAndView ExeptionHandler(ModelAndView modelAndView){
//发生异常处理
return modelAndView;
}
```



##### 基于全局定义异常处理器

```java
package com.demo.order.exceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class OrderExceptionHandler {
    @ExceptionHandler
    public ModelAndView OneExceptionHandler(ModelAndView modelAndView){
        //发生异常处理
        return modelAndView;
    }
}
```



##### 基于全局定义异常处理器简单版

```java
/**
 * 配置异常处理器
 * @return 异常处理器
 */
@Bean
public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
    SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
    Properties properties = new Properties();
    properties.put("异常类全包名","要跳转的页面");
    simpleMappingExceptionResolver.setExceptionMappings(properties);
    return simpleMappingExceptionResolver;
}
```



##### 基于全局定义异常处理器自定义版

```java
/**
 * 配置自定义全局异常处理器
 *
 * @return 自定义全局异常处理器
 */
@Bean
public HandlerExceptionResolver handlerExceptionResolver() {
    /*
      request 请求对象
      response 响应对象
      execute 处理器对象
      ex 异常对象
     */
    return (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) -> {
        ModelAndView modelAndView = new ModelAndView();
        // 异常处理
        return modelAndView;
    };
}
```



-----



### 注解详情

| 注解                  | 说明                                                         |
| --------------------- | ------------------------------------------------------------ |
| @GetMapping           | 仅支持Get请求，一般用于查询操作                              |
| @PostMapping          | 仅支持Post请求，一般用于新增操作                             |
| @PutMapping           | 仅支持Put请求，一般用于更新操作                              |
| @DeleteMapping        | 仅支持Delete请求，一般用于删除操作                           |
| @PathVariable         | 加在方法参数上，用来获取Url中对应的参数，Value为占位符名称   |
| @RequsetBody          | 作用于类，方法，参数上，表示接收Json格式的数据，一边用于接收请求体body中的Json信息 |
| @ResponseBody         | 加在方法上，表示当前方法返回Json格式数据，加在类上表示影响本类所有方法 |
| @RestController       | 相当于@Controller+@ResponseBody                              |
| @ExceptionHandler     | 加在方法上，返回ModelAndView，发生异常触发此异常处理器       |
| @ControllerAdvice     | 加在类上，定义全局异常处理器，类中定义ExceptionHandler       |
| @RestControllerAdvice | 相当于@ControllerAdvice+@ResponseBody                        |
| @CrossOrigin          | 支持请求跨域访问                                             |
| @RequestParam         | 用于从请求中获取查询参数QueryParameters或表单数据            |
| @RequestPart          | 用于处理请求的`multipart/form-data`类型的数据，通常用于文件上传 |