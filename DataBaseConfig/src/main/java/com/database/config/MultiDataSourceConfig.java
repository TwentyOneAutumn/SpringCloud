package com.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;

public class MultiDataSourceConfig {

    @Bean
    public MultiDataSourceFactory multiDataSourceFactory(){
        return new MultiDataSourceFactory();
    }
}
