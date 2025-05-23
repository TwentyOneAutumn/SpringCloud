package com.wecom.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import com.wecom.domain.entry.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestBuild {

    private static final String token = "";

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
                .header("X-Auth",token)
                .execute();
        String body = response.body();
        return new JSONObject(body).toBean(clazz);
    }

    /**
     * 发送Post请求并获取响应
     */
    public static <T> T post(String url, Map<String, String> paramMap,Class<T> clazz) {
        HttpResponse response = HttpRequest.post(url)
                .body(new JSONObject(paramMap).toString())
                .header("X-Auth",token)
                .execute();
        String body = response.body();
        return new JSONObject(body).toBean(clazz);
    }
}
