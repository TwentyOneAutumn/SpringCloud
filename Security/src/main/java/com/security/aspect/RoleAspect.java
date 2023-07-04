package com.security.aspect;

import cn.hutool.core.bean.BeanUtil;
import com.security.config.UserDetailsImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAspect {

    @Pointcut("execution(* org.springframework.security.authentication.ProviderManager.authenticate(*))")
    public void pointcut(){}

    @AfterReturning(value = "pointcut()",returning = "returnValue")
    public void before(JoinPoint joinPoint,Object returnValue){
        if(returnValue instanceof Authentication){
            Authentication returnValue1 = (Authentication) returnValue;
            Object principal = returnValue1.getPrincipal();
            if(principal instanceof UserDetailsImpl){
                UserDetailsImpl userDetails = (UserDetailsImpl) principal;
                System.out.println(userDetails);
            }
            System.out.println(principal.getClass().getName());
        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        if(principal instanceof UserDetailsImpl){
//            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
//            System.out.println(userDetails);
//        }
//        System.out.println(principal.getClass().getName());
    }
}
