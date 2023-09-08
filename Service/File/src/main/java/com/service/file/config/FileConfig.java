package com.service.file.config;

import io.minio.MinioClient;
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
    private final String endpoint = "http://124.221.27.253:9000";

    /**
     * 账号
     */
    private final String accessKey = "root";

    /**
     * 密码
     */
    private final String secretKey = "2762581@com";

    /**
     * 配置Minio客户端
     * @return MinioClient
     */
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(endpoint).credentials(accessKey,secretKey).build();
    }
}
