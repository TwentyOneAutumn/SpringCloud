package com.demo.Core.Utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtil {

    /**
     * 获取Token
     * @param key 密匙
     * @param userName 用户名
     * @param passWord 密码
     * @return Token
     */
    public static String getToken(String key, String userName, String passWord) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 30);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 载荷
        payload.put("userName", userName);
        payload.put("passWord", passWord);
        // 根据荷载和Key生成Token并返回
        return JWTUtil.createToken(payload, key.getBytes());
    }

    /**
     * 校验Token是否有效
     * @param token Token
     * @param key 密匙
     * @return true:有效 false:无效
     */
    public static boolean verifyToken(String token, String key) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.setKey(key.getBytes()).verify() && jwt.validate(0);
    }
}
