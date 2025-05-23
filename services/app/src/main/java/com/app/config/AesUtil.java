package com.app.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.AES;

public class AesUtil {

    private static final AES aes = new AES(
            // 模式
            "CBC",
            // 补码方式
            "PKCS7Padding",
            // 密钥
            "5bfc2c604725ba6e10813a88826ec9fe".getBytes(),
            // iv加盐
            "DYgjCEIMVrj2W9xN".getBytes()
    );


    /**
     * 加密
     */
    public static String encrypt(String content) {
        return aes.encryptHex(content);
    }


    /**
     * 解密
     */
    public static String decrypt(String content) {
        return aes.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
    }
}
