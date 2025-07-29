package com.wecom.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class RequestBuild {

    private static final String token = "83961ac1-0f8a-11f0-a139-fefcfeb6e479";

    /**
     * 发送Get请求并获取响应
     */
    public static <T> T get(String url, Map<String, String> paramMap, Class<T> clazz) {
        if (MapUtil.isNotEmpty(paramMap)) {
            List<String> joinList = new ArrayList<>();
            paramMap.forEach((k, v) -> joinList.add(k + "=" + v));
            url = url + "?" + String.join("&", joinList);
        }
        HttpResponse response = HttpRequest.get(url)
                .header("x-auth",token)
                .execute();
        String body = response.body();
        return new JSONObject(body).toBean(clazz);
    }

    /**
     * 发送Get请求并获取响应
     */
    public static String get(String url, Map<String, String> paramMap) {
        if (MapUtil.isNotEmpty(paramMap)) {
            List<String> joinList = new ArrayList<>();
            paramMap.forEach((k, v) -> joinList.add(k + "=" + v));
            url = url + "?" + String.join("&", joinList);
        }
        HttpResponse response = HttpRequest.get(url)
                .header("x-auth",token)
                .execute();
        return response.body();
    }

    /**
     * 发送Post请求并获取响应
     */
    public static <T> T post(String url, Map<String, String> paramMap,Class<T> clazz) {
        HttpResponse response = HttpRequest.post(url)
                .body(new JSONObject(paramMap).toString())
                .header("x-auth",token)
                .execute();
        String body = response.body();
        return new JSONObject(body).toBean(clazz);
    }

    /**
     * 发送Post请求并获取响应
     */
    public static String post(String url, Map<String, String> paramMap) {
        HttpResponse response = HttpRequest.post(url)
                .body(new JSONObject(paramMap).toString())
                .header("x-auth",token)
                .execute();
        return response.body();
    }
}
