package com.wecom.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class UserInfo {

    /**
     * 错误代码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 用户ID
     */
    private String userid;

    /**
     * 用户票据
     */
    private String userTicket;

    /**
     * 构建对象
     */
    public static UserInfo build(String result) {
        JSONObject json = new JSONObject(result);
        UserInfo pojo = json.toBean(UserInfo.class);
        String ticket = json.get("user_ticket", String.class);
        if(StrUtil.isNotBlank(ticket)){
            pojo.setUserTicket(ticket);
        }
        return pojo;
    }
}
