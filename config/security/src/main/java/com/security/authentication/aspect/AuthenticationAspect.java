package com.security.authentication.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.security.authentication.beans.ThreadCache;
import com.security.authentication.beans.AuthenticationCache;
import com.security.authentication.beans.PermissionPostProcessor;
import com.security.authentication.exceptions.PermissionVerifyException;
import com.security.authentication.interfaces.AuthenticationAware;
import com.security.authentication.interfaces.AuthenticationPostProcessor;
import com.security.enums.FeignRequestHeader;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 权限增强器切面类
 */
@Slf4j
@Aspect
public class AuthenticationAspect implements BeanFactoryPostProcessor {

    /**
     * 权限增强器集合
     */
    @Autowired
    private List<AuthenticationPostProcessor> authenticationPostProcessorList;

    /**
     * 切点
     */
    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore.readAuthentication(org.springframework.security.oauth2.common.OAuth2AccessToken))")
    public void pointcut(){}


    /**
     * 通后置返回通知
     * @param returnValue 返回值
     */
    @AfterReturning(value = "pointcut()",returning = "returnValue")
    public void before(Object returnValue) {
        // 获取缓存
        AuthenticationCache cache = ThreadCache.getCache(AuthenticationCache.class);
        // 盘空
        if(BeanUtil.isNotEmpty(cache)){
            // 获取缓存中的请求对象
            HttpServletRequest request = cache.getRequest();
            // 判断是否为Feign请求
            if(!FeignRequestHeader.isFeignRequest(request)){
                try {
                    Authentication authentication = (Authentication) returnValue;
                    authenticationPostProcessorList.forEach(authenticationPostProcessor -> authenticationPostProcessor.postProcess(authentication));
                }
                catch (Exception e) {
                    // 打印log
                    log.error("AuthenticationPostProcessor.postProcess()方法校验异常:{}",e.getMessage());
                    // 设置异常信息,AfterValidationFilter后置过滤器中会使用
                    cache.setError(true);
                    String errorMsg = "权限验证异常";
                    if(e instanceof PermissionVerifyException){
                        errorMsg = e.getMessage();
                    }
                    cache.setErrorMsg(errorMsg);
                    cache.setException(e);
                }
            }
        }
    }

    /**
     * 注册默认的权限增强器
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException 异常
     */
    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if(CollUtil.isEmpty(authenticationPostProcessorList)){
            beanFactory.registerSingleton("permissionPostProcessor",new PermissionPostProcessor());
            PermissionPostProcessor permissionPostProcessor = beanFactory.getBean("permissionPostProcessor", PermissionPostProcessor.class);
            Map<String, AuthenticationAware> awareMap = beanFactory.getBeansOfType(AuthenticationAware.class);
            if(CollUtil.isNotEmpty(awareMap)){
                List<AuthenticationAware> awareList = new ArrayList<>(awareMap.values());
                permissionPostProcessor.setAwareList(awareList);
            }
            List<AuthenticationPostProcessor> authenticationPostProcessorList = new ArrayList<>();
            authenticationPostProcessorList.add(permissionPostProcessor);
            this.authenticationPostProcessorList = authenticationPostProcessorList;
        }
    }
}
