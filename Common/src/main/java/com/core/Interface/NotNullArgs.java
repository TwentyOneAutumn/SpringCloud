package com.core.Interface;

import com.core.config.NotNullArgsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotNullArgsValidator.class)
public @interface NotNullArgs {

    String message() default "测试NotNullArgs注解";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
