package com.wecom.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业微信配置
 */
public class OMSConfig {

    /**
     * 获取用户信息
     */
    public static final String USER_INFO = "http://127.0.0.1:7878/test/test3";

    /**
     * 获取用户信息参数
     */
    public static Map<String, String> userParam(String userName,String mobile) {
        Map<String, String> param = new HashMap<>();
        param.put("userName", userName);
        param.put("mobile", mobile);
        return param;
    }
}
