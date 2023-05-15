package com.security.doMain;

import cn.hutool.core.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义密码加密
 */
@Component
public class SecurityPasswordEncoder implements PasswordEncoder {


    /**
     * 对原始密码进行加密，返回加密后的密码
     * @param rawPassword 密码
     * @return 加密后的密码
     */
    @Override
    public String encode(CharSequence rawPassword) {
        // 循环加密
        for (int i = 0; i < 10; i++) {
            rawPassword = Base64.encode(rawPassword);
        }
        return rawPassword.toString();
    }


    /**
     * 将登录输入的密码和数据库取出的密码进行比较
     * @param rawPassword 登录输入的密码
     * @param encodedPassword 从数据库获取的加密后的密码
     * @return 密码是否匹配 true:匹配 false:不匹配
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 将输入密码加密
        String encodeToRawPassword = encode(rawPassword);
        // 将密码进行比较
        return encodedPassword.equals(encodeToRawPassword.toString());
    }
}
