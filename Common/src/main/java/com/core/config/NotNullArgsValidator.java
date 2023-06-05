package com.core.config;

import com.core.Interface.NotNullArgs;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullArgsValidator implements ConstraintValidator<NotNullArgs, String> {


    @Override
    public void initialize(NotNullArgs constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
