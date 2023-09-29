package com.core.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = {"com.core.config"})
public class CommonConfig {

    /**
     * 设置Feign的超时时间
     * @return options
     */
//    @Bean
//    public Request.Options options(){
//        // connectTimeout 连接超时时间
//        // readTimeout 读取超时时间
//        return new Request.Options(5, TimeUnit.SECONDS,5,TimeUnit.SECONDS,true);
//    }

}
