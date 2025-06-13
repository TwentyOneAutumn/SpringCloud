package com.security.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.core.domain.Build;
import com.security.enums.PermitUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Token校验过滤器
 */
@Slf4j
public class RedisTokenAuthenticationFilter extends OncePerRequestFilter {

    private final RedisOAuth2AuthorizationService authorizationService;

    public RedisTokenAuthenticationFilter(RedisOAuth2AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // 判断是否是需要放行接口
            String requestURI = request.getRequestURI();
            boolean anyMatch = Arrays.stream(PermitUrl.UrlArr).anyMatch(regex -> Pattern.compile(regex).matcher(requestURI).find());
            if(!anyMatch){
                // 获取授权头信息
                String header = request.getHeader("Authorization");
                if (StrUtil.isNotBlank(header) && header.startsWith("Bearer ")) {
                    String token = header.substring(7);
                    // 通过Token获取授权信息
                    Authentication authenticate = authorizationService.getToken(token);
                    // 填充上下文信息
                    SecurityContextHolder.getContext().setAuthentication(authenticate);
                }else {
                    writerError(response,"Token format is Authorization:Bearer <token>");
                    return;
                }
            }
        }catch (Exception e){
            log.error("校验Token合法性异常:{}",e.getMessage());
            writerError(response,e.getMessage());
            return;
        }
        // 执行下一个过滤器
        filterChain.doFilter(request, response);
    }


    /**
     * 构建错误信息并写入响应
     */
    private void writerError(HttpServletResponse response,String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(new JSONObject(Build.result(false,msg)).toString());
        out.flush();
        out.close();
    }
}
