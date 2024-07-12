package com.security.authentication.filter;

import cn.hutool.core.bean.BeanUtil;
import com.core.doMain.Build;
import com.core.utils.ResponseUtils;
import com.security.authentication.beans.ThreadCache;
import com.security.authentication.beans.AuthenticationCache;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 后置权限校验过滤器
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class AfterValidationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取缓存
        AuthenticationCache cache = ThreadCache.removeCache(AuthenticationCache.class);
        // 判空 是否异常
        if(BeanUtil.isNotEmpty(cache) && cache.isError()){
            // 写入异常信息并响应
            ResponseUtils.writer((HttpServletResponse) response, Build.ajax(false,cache.getErrorMsg()));
        }
        chain.doFilter(request, response);
    }
}
