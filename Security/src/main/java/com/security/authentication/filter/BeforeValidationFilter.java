package com.security.authentication.filter;

import cn.hutool.core.util.StrUtil;
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

    /**
     * Feign请求头
     */
    private final String FEIGN_REQUEST_HEADER = "FeignRequest";

    /**
     * 请求对象
     */
    private HttpServletRequest request;

    /**
     * 响应对象
     */
    private HttpServletResponse response;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.request = (HttpServletRequest)request;
        this.response = (HttpServletResponse)response;
        chain.doFilter(request, response);
    }

    /**
     * 是否为Feign请求
     * @return boolean
     */
    public boolean isFeignRequest(){
        return StrUtil.isNotEmpty(request.getHeader(FEIGN_REQUEST_HEADER));
    }
}
