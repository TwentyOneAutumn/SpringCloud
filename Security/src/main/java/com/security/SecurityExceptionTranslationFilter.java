package com.security;

import com.core.utils.TimeUtils;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.time.LocalDateTime;
import java.util.Date;

public class SecurityExceptionTranslationFilter extends ExceptionTranslationFilter {
    public SecurityExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }
}
