package com.security.enums;

/**
 * SpringSecurity放行接口
 */
public class PermitUrl {
    public static final String[] UrlArr = {
            "/token/get",
            "/user/info",
            "/user/check",
            "/user/add",
            "/user/test"
    };
}
