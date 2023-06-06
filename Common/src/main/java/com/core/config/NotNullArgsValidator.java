package com.core.config;

import com.core.Interface.NotNullArgs;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullArgsValidator implements ConstraintValidator<NotNullArgs, Object> {


    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        // 校验逻辑
        return false;
    }
}
