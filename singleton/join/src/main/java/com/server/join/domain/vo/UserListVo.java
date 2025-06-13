package com.server.join.domain.vo;

import lombok.Data;

@Data
public class UserListVo {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String city;
    private String address;
}
