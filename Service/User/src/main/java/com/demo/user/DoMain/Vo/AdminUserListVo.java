package com.demo.user.DoMain.Vo;

import lombok.Data;

@Data
public class AdminUserListVo {
    private String userId;
    private String userName;
    private String passWord;
    private String role;
    private Boolean isDeleted;
}
