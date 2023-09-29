package com.seata.config;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class SeataConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${seata.tx-service-group}")
    private String txServiceGroup;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner(applicationName, txServiceGroup);
    }
}
