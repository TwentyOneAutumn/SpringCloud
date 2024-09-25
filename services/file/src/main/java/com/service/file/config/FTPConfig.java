package com.service.file.config;

import com.service.file.domain.FTPEntity;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FTPConfig {

    @Bean
    @ConfigurationProperties("ftp")
    public FTPEntity ftpEntity() {
        return new FTPEntity();
    }
}
