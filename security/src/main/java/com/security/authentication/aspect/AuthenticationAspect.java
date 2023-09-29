package com.security.authentication.aspect;

import cn.hutool.core.collection.CollUtil;
import com.security.authentication.Interface.AuthenticationAware;
import com.security.authentication.filter.BeforeValidationFilter;
import com.security.authentication.beans.PermissionPostProcessor;
import com.security.authentication.beans.PermissionVerifyException;
import com.security.authentication.beans.AuthenticationExceptionProcessor;
import com.security.authentication.Interface.AuthenticationPostProcessor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 权限增强器切面类
 */
@Aspect
@Component
public class AuthenticationAspect implements BeanFactoryPostProcessor {

    /**
     * 注册权限增强异常处理器
     */
    private final AuthenticationExceptionProcessor authenticationExceptionProcessor;

    /**
     * 权限增强器集合
     */
    private List<AuthenticationPostProcessor> authenticationPostProcessorList;

    /**
     * 前置拦截器
     */
    private BeforeValidationFilter beforValidationFilter;

    /**
     * 切点
     */
    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore.readAuthentication(org.springframework.security.oauth2.common.OAuth2AccessToken))")
    public void pointcut(){}

    public AuthenticationAspect(AuthenticationExceptionProcessor authenticationExceptionProcessor,List<AuthenticationPostProcessor> authenticationPostProcessorList,BeforeValidationFilter beforValidationFilter){
        this.authenticationExceptionProcessor = authenticationExceptionProcessor;
        this.authenticationPostProcessorList = authenticationPostProcessorList;
        this.beforValidationFilter = beforValidationFilter;
    }


    /**
     * 通后置返回通知
     * @param returnValue 返回值
     */
    @AfterReturning(value = "pointcut()",returning = "returnValue")
    public void before(Object returnValue) {
        if(!beforValidationFilter.isFeignRequest()){
            // 初始化异常状态
            authenticationExceptionProcessor.init();
            try {
                Authentication authentication = (Authentication) returnValue;
                authenticationPostProcessorList.forEach(authenticationPostProcessor -> authenticationPostProcessor.postProcess(authentication));
            }
            catch (Exception e) {
                e.printStackTrace();
                // 设置异常状态
                authenticationExceptionProcessor.setError(true);
                String errorMsg = "权限验证异常";
                if(e instanceof PermissionVerifyException){
                    errorMsg = e.getMessage();
                }
                authenticationExceptionProcessor.setErrorMsg(errorMsg);
                authenticationExceptionProcessor.setException(e);
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
