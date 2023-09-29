package com.security.authentication.annotation;

import com.security.authentication.config.AuthenticationPostProcessorConfig;
import com.security.authentication.config.ValidationFilterConfig;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * 开启权限校验增强器
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({AuthenticationPostProcessorConfig.class, ValidationFilterConfig.class})
public @interface EnableAuthenticationPostProcessor {

}
