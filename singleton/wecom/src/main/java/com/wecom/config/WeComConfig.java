package com.wecom.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业微信配置
 */
public class WeComConfig {

    /**
     * 企业微信Token
     */
    public static String TOKEN = "";

    /**
     * 获取企业微信用户信息
     */
//    public static final String WECOM_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
    public static final String WECOM_USER_INFO = "http://127.0.0.1:7878/test/test2";

    /**
     * 获取企业微信Token
     */
//    public static final String WECOM_TOKEN_INFO = "https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";
    public static final String WECOM_TOKEN_INFO = "http://127.0.0.1:7878/test/test1";

    /**
     * 获取Token参数
     */
    public static final Map<String, String> TOKEN_PARAMS = new HashMap<>();

    /**
     * 获取用户信息
     */
    public static final String USER_INFO = "http://127.0.0.1:7878/test/test3";

    static {
        // 初始化获取企业微信Token参数
        TOKEN_PARAMS.put("suite_id", "wwddddccc7775555aaa");
        TOKEN_PARAMS.put("suite_secret", "ldAE_H9anCRN21GKXVfdAAAAAAAAAAAAAAAAAA");
        TOKEN_PARAMS.put("suite_ticket", "Cfp0_givEagXcYJIztF6sfbdmIZCmpaR8ZBsvJEFFNBrWmnD5-CGYJ3_NhYexMyw");
    }


    /**
     * 获取企业微信用户信息参数
     */
    public static Map<String, String> wecomUserParam(String userId) {
        Map<String, String> param = new HashMap<>();
        param.put("access_token", TOKEN);
        param.put("userid", userId);
        return param;
    }

    /**
     * 获取用户信息参数
     */
    public static Map<String, String> userParam(String userId) {
        Map<String, String> param = new HashMap<>();
        param.put("access_token", TOKEN);
        param.put("userid", userId);
        return param;
    }
}
