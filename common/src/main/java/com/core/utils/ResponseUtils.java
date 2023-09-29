package com.core.utils;

import cn.hutool.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具类
 */
public class ResponseUtils {


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
