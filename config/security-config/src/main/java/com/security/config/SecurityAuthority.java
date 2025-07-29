package com.security.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private String role;

    @Override
    public String getAuthority() {
        return "";
    }

    public static SecurityAuthority build(String role) {
        return new SecurityAuthority(role);
    }
}
