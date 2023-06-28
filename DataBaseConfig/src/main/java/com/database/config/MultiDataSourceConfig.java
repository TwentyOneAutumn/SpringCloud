package com.database.config;

import org.springframework.context.annotation.Bean;

public class MultiDataSourceConfig {

    @Bean
    public MultiDataSourceFactory multiDataSourceFactory(){
        return new MultiDataSourceFactory();
    }
}
