package com.service.basic.config;

import com.service.basic.timedTask.TestTask;
import org.springframework.context.annotation.Bean;

public class TestConfig {

    @Bean
    public TestTask testTask(){
        return new TestTask();
    }
}
