package com.wecom.domain.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    private Integer errcode;
    private String errmsg;
    private String userid;
    private String name;
    private String mobile;
}
