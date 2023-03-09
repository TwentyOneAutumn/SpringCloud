package com.demo.user.Intercept;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截器
 */
@Component
public class AuthIntercept implements HandlerInterceptor {

    /**
     * 前置拦截器
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 控制器对象
     * @return true:执行下一个拦截器 false:拦截请求,并应在该拦截器中处理响应结果
     * @throws Exception 如果拦截器内部发生错误则抛出异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 编写拦截或放行逻辑代码
        return true;
    }
}
