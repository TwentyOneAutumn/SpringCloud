package com.collect.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONObject;
import com.collect.domain.RequestInfo;
import com.collect.kafka.LogSendClient;
import com.collect.utils.IPUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * RestController请求切面类
 */
@Slf4j
@Aspect
@Component
public class RestControllerAspect {

    private final String TOPIC = "request-out-0";

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    /**
     * 服务名
     */
    @Value("${spring.application.name:#{未知}}")
    private String module;

    // 定义切点，拦截 @GetMapping 注解的方法
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMappingMethods() {
    }

    // 定义切点，拦截 @PostMapping 注解的方法
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMappingMethods() {
    }

    // 定义切点，拦截 @PutMapping 注解的方法
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMappingMethods() {
    }

    // 定义切点，拦截 @DeleteMapping 注解的方法
    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMappingMethods() {
    }

    // 组合切点：拦截 @GetMapping @PostMapping @PutMapping @DeleteMapping 注解标记的方法
    @Pointcut("getMappingMethods() || postMappingMethods() || putMappingMethods() || deleteMappingMethods()")
    public void allRequestMappings() {
    }

    /**
     * 环绕通知
     */
    @Around(value = "allRequestMappings()")
    public Object restControllerAround(ProceedingJoinPoint point) throws Throwable {
        // 获取目标方法对象
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        HttpServletRequest request = getHttpServletRequest();
        RequestInfo pojo = new RequestInfo();
        // 模块名
        pojo.setModule(module);
        // 时间戳
        pojo.setTimestamp(LocalDateTime.now().format(format));

        // 1.获取目标方法HTTP请求类型
        String requestType = getRequestType(method);

        // 2.获取请求IP地址
        String ipAddr = BeanUtil.isNotEmpty(request) ? IPUtil.getIpAddr(request) : null;

        // 3.获取请求URI
        String url = BeanUtil.isNotEmpty(request) ? request.getRequestURI() : null;

        // 4.获取并构建参数JSON对象
        String parameterJson = getParameterJson(method, point.getArgs());

        // 5.执行目标方法并获取返回结果
        Object result = null;

        // 请求类型
        pojo.setRequestType(requestType);
        // 请求IP地址
        pojo.setIp(ipAddr);
        // 请求URL
        pojo.setUrl(url);
        // 请求参数
        pojo.setParam(parameterJson);
        String startTime = null;
        String endTime = null;
        try {
            // 开始时间
            startTime = LocalDateTime.now().format(format);

            // 执行目标方法
            result = point.proceed();

            // 结束时间
            endTime = LocalDateTime.now().format(format);
        } catch (Exception exception) {
            // 6.记录目标方法抛出的异常信息
            pojo.setErrorType(exception.getClass().getName());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            pojo.setStackTrace(sw.toString());

            // 发送日志信息到Kafka
            LogSendClient.send(TOPIC,new JSONObject(pojo).toString());

            // 重新抛出异常
            throw exception;
        }

        // 开始时间
        pojo.setStartTime(startTime);
        // 结束时间
        pojo.setEndTime(endTime);
        // 返回结果
        pojo.setResult(new JSONObject(result).toString());

        // 7.发送日志信息到Kafka
        LogSendClient.send(TOPIC,new JSONObject(pojo).toString());

        // 返回结果
        return result;
    }


    /**
     * 获取当前上下文的请求对象
     */
    private HttpServletRequest getHttpServletRequest(){
        // 获取当前请求的 ServletRequestAttributes 对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (BeanUtil.isNotEmpty(attributes)) {
            return attributes.getRequest();
        }
        return null;
    }

    /**
     * 获取HTTP请求类型
     */
    private String getRequestType(Method method){
        String requestType = null;
        if (method.isAnnotationPresent(GetMapping.class)) {
            requestType = "GET";
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            requestType = "POST";
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            requestType = "PUT";
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            requestType = "DELETE";
        }
        return requestType;
    }


    /**
     * 构建参数JSON
     */
    private String getParameterJson(Method method,Object[] args){
        Parameter[] parameters = method.getParameters();
        LinkedHashMap<String, Object> parameterMap = new LinkedHashMap<>();
        if(ArrayUtil.isNotEmpty(parameters) && ArrayUtil.isNotEmpty(args)){
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                String name = parameter.getName();
                Object value = args[i];
                Class<?> type = parameter.getType();
                // 排除请求响应对象
                if (!type.equals(HttpServletRequest.class) && !type.equals(HttpServletResponse.class)) {
                    parameterMap.put(name, value);
                }
            }
        }
        return new JSONObject(parameterMap).toString();
    }
}
