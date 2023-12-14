package com.security.authentication.filter;

import com.core.utils.ThreadCache;
import com.security.authentication.beans.AuthenticationCache;
import lombok.Data;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前置权限校验过滤器
 */
@Data
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BeforeValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 缓存当前请求信息,后续AuthenticationAspect中需要用到
        ThreadCache.setCache(AuthenticationCache.create((HttpServletRequest)request,(HttpServletResponse)response));
        chain.doFilter(request, response);
    }
}
