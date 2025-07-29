package com.security.config;

import lombok.Data;

@Data
public class AccessToken {
    private String access_token;
    private String scope;
    private String token_type;
    private Integer expires_in;
    private String expiration_time;
}
