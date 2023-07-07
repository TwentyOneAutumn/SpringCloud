package com.security.authentication.filter;

import com.core.doMain.Build;
import com.core.utils.ResponseUtils;
import com.security.authentication.beans.AuthenticationExceptionProcessor;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 后置权限校验过滤器
 */
public class AfterValidationFilter implements Filter {

    /**
     * 权限校验异常处理器
     */
    private final AuthenticationExceptionProcessor authenticationExceptionProcessor;

    public AfterValidationFilter(AuthenticationExceptionProcessor authenticationExceptionProcessor){
        this.authenticationExceptionProcessor = authenticationExceptionProcessor;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(authenticationExceptionProcessor.isError()){
            ResponseUtils.writer((HttpServletResponse) response, Build.buildAjax(false,authenticationExceptionProcessor.getErrorMsg()));
        }else {
            chain.doFilter(request, response);
        }
    }
}
