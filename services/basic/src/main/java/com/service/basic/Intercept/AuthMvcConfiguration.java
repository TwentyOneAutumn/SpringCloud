package com.service.basic.Intercept;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * MVC配置类
 */
@Component
public class AuthMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    AuthIntercept authIntercept;

    /**
     * 重写此方法以添加SpringMVC拦截器,用于控制器调用的预处理和后处理
     * @param registry 配置映射的拦截器链对象
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 在拦截器链中添加一个拦截器
        registry.addInterceptor(authIntercept);
        super.addInterceptors(registry);
    }

    /**
     * 重写此方法以添加自定义HttpMessageConverters,RequestMappingHandlerAdapter,ExceptionHandlerExceptionResolver
     * 将转换器添加到列表将关闭默认转换器,否则默认情况下将注册这些转换器
     * @param converters 消息转换器列表对象
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        // 设置基本序列化
        builder.modulesToInstall(new JavaTimeModule());
        // 设置LocalDateTime序列化和反序列化格式
        builder.serializerByType(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        builder.deserializerByType(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 设置LocalDate序列化和反序列化格式
        builder.serializerByType(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        builder.deserializerByType(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 添加到转换器链
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}
