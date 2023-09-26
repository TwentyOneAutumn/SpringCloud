package com.core.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * Http请求工具类
 */
public class HttpUtils {

    /**
     * 发送HttpGet请求
     * 如果请求请求状态码不成功则抛出异常
     * @param url 请求URL
     * @param clazz 响应类型
     * @param params 参数映射
     * @return 响应对象
     */
    public static <T> T get(String url, Class<T> clazz, Map<String,Object> params){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> response = restTemplate.getForEntity(url,clazz,params);
        isSuccess(response.getStatusCodeValue());
        return response.getBody();
    }

    /**
     * 发送HttpGet请求
     * 如果请求请求状态码不成功则抛出异常
     * @param url 请求URL
     * @param clazz 响应类型
     * @return 响应对象
     */
    public static <T> T get(String url, Class<T> clazz){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> response = restTemplate.getForEntity(url,clazz);
        isSuccess(response.getStatusCodeValue());
        return response.getBody();
    }

    /**
     * 发送HttpGet请求
     * 如果请求请求状态码不成功则抛出异常
     * @param url 请求URL
     * @return 响应对象
     */
    public static String get(String url){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        isSuccess(response.getStatusCodeValue());
        return response.getBody();
    }

    /**
     * 发送HttpPost请求
     * 如果请求请求状态码不成功则抛出异常
     * @param url 请求URL
     * @param body 请求Body
     * @param headerMap 请求头
     * @param clazz 响应类型
     * @return 响应对象
     */
    public static <T> T post(String url, Object body, Map<String,String> headerMap, Class<T> clazz){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = new HttpEntity(body);
        HttpHeaders headers = httpEntity.getHeaders();
        headerMap.forEach(headers::set);
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<T> response = restTemplate.postForEntity(url, httpEntity, clazz);
        isSuccess(response.getStatusCodeValue());
        return response.getBody();
    }

    /**
     * 发送HttpPost请求
     * 如果请求请求状态码不成功则抛出异常
     * @param url 请求URL
     * @param headerMap 请求头
     * @param clazz 响应类型
     * @return 响应对象
     */
    public static <T> T post(String url, Map<String,String> headerMap, Class<T> clazz){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        headerMap.forEach(httpHeaders::set);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<T> response = restTemplate.postForEntity(url, httpEntity, clazz);
        isSuccess(response.getStatusCodeValue());
        return response.getBody();
    }

    /**
     * 发送HttpPost请求
     * 如果请求请求状态码不成功则抛出异常
     * @param url 请求URL
     * @param clazz 响应类型
     * @return 响应对象
     */
    public static <T> T post(String url, Class<T> clazz){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type",MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<T> response = restTemplate.postForEntity(url, httpEntity, clazz);
        isSuccess(response.getStatusCodeValue());
        return response.getBody();
    }

    /**
     * 判断Http状态码是否成功
     * @param statusCode Http状态码
     * @return boolean
     */
    public static boolean isSuccessStatus(int statusCode){
        return statusCode >= 200 && statusCode < 300;
    }

    /**
     * 如果状态码不成功则抛出异常
     * @param statusCode Http状态码
     */
    public static void isSuccess(int statusCode){
        if(!isSuccessStatus(statusCode)){
            throw new RuntimeException("HTTP请求异常");
        }
    }
}
