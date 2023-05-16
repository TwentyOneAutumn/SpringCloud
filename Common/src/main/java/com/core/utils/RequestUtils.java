package com.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.core.enums.FeignRequestHeater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求工具类
 */
public class RequestUtils {

    /**
     * 获取请求头
     * @param request 请求对象
     * @param headerName 请求头名称
     * @return 请求头值
     */
    public static String getHeader(HttpServletRequest request,String headerName){
        return request.getHeader(headerName);
    }

    /**
     * 判断请求头是否存在
     * @param request 请求对象
     * @param headerName 请求头名称
     * @return 请求头存在则返回true,反之则返回false
     */
    public static boolean isExistHeader(HttpServletRequest request,String headerName){
        String header = RequestUtils.getHeader(request, headerName);
        return StrUtil.isNotEmpty(header);
    }

    /**
     * 获取所有请求头信息
     * @param request 请求对象
     * @return 请求头Map
     */
    public static Map<String,String> getHeaders(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> map = new HashMap<>();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            map.put(headerName,request.getHeader(headerName));
        }
        return map;
    }

    /**
     * 获取URL，包括协议，IP，端口号，请求路径
     * @param request 请求对象
     * @return Url
     */
    public static String getRequestUrl(HttpServletRequest request){
        return request.getRequestURL().toString();
    }

    /**
     * 获取URL，返回请求路径
     * @param request 请求对象
     * @return Url
     */
    public static String getRequestUri(HttpServletRequest request){
        return request.getRequestURI();
    }

    /**
     * 写入响应信息
     * @param response 响应对象
     * @param obj 响应信息对象，该对象会被序列化为Json格式，并写入响应
     * @param <T> 泛型
     * @throws IOException 异常
     */
    public static <T> void writer(HttpServletResponse response, T obj) throws IOException {
        // 设置编码格式
        response.setCharacterEncoding("UTF-8");
        // 设置响应格式
        response.setHeader("Content-Type","application/json");
        response.getWriter().write(new JSONObject(obj).toString());
    }
}
