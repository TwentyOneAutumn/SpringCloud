package com.demo.user.Intercept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置拦截器
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
}
