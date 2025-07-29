package com.wecom.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class AccessToken {

    /**
     * 错误代码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * Token
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Integer expiresIn;

    /**
     * 构建方法
     */
    public static AccessToken build(String result){
        JSONObject json = new JSONObject(result);
        AccessToken pojo = json.toBean(AccessToken.class);
        String token = json.get("access_token", String.class);
        if(StrUtil.isNotBlank(token)){
            pojo.setAccessToken(token);
        }
        Integer expiresIn = json.get("expires_in", Integer.class);
        if(BeanUtil.isNotEmpty(expiresIn)){
            pojo.setExpiresIn(expiresIn);
        }
        return pojo;
    }
}
