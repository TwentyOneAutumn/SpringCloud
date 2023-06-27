package com.service.basic.Interface;

import com.service.basic.config.TestConfig;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TestConfig.class)
@Documented
public @interface Config {
}
