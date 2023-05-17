package com.security.config;

import cn.hutool.core.util.StrUtil;
import com.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token解析过滤器
 */
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = RequestUtils.getHeader(request,"Authorization");
        if (StrUtil.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // 获取Token
            String token = authorizationHeader.substring(7);
            ResourceServerConfigurerAdapter
            // 验证和解析Token的逻辑
            // 将Token中的信息填充到Security上下文中
        }

        filterChain.doFilter(request, response);
    }
}
