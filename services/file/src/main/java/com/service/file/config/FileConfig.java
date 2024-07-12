package com.service.file.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件模块配置累
 */
@Configuration
public class FileConfig {

    /**
     * Minio地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * 账号
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * 密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 配置Minio客户端
     * @return MinioClient
     */
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(endpoint).credentials(accessKey,secretKey).build();
    }
}
