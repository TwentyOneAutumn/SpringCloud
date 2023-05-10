package com.Core.Interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.Core.Lock.RedisCacheLock;
import com.Core.Utils.ThreadUtils;
import com.demo.Core.Utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Feign远程调用拦截器
 */
@Component
public class FeignHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    RedisCacheLock redisCacheLock;

    /**
     * 前置拦截器
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 拦截器对象
     * @return 是否放行
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头
        String serviceName = request.getHeader("FeignRequestService");
        String url = request.getHeader("FeignRequestUrl");
        // 如果没有请求头，则不是Feign远程调用，放行请求
        if(StrUtil.isEmpty(serviceName) || StrUtil.isEmpty(url)){
            return true;
        }else {
            // 是否发生异常标识
            boolean isError = false;
            try {
                // 存储服务名称到线程变量中
                ThreadUtils.setThreadLocal(serviceName);
                // 判断当前缓存锁是否被占用
                if(redisCacheLock.isLocked()){
                    // 如果被占用，证明正在更新缓存
                    isError = true;
                }else {
                    // 获取缓存数据
                    Object cacheData = ClassUtils.invokeMethod(request, ClassUtils.toMethod(serviceName, url));
                    // 设置编码格式
                    response.setCharacterEncoding("UTF-8");
                    // 设置响应状态码
                    response.setStatus(200);
                    // 将缓存数据写入响应
                    PrintWriter writer = response.getWriter();
                    // 写入JSON化后的数据
                    writer.write(new JSONObject(cacheData,false).toString());
                    // 关闭流
                    writer.close();
                }
            }
            catch (Exception e){
                // 如果发生异常则放行请求
                isError = true;
            }
            return isError;
        }
    }

    /**
     * 后置拦截器
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 拦截器对象
     * @throws Exception 异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 最终拦截器
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 拦截器对象
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
