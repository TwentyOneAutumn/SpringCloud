package com.wecom.domain;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import lombok.Data;

@Data
public class UserDetail {

    /**
     * 错误代码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 电话号码
     */
    private String mobile;
}
