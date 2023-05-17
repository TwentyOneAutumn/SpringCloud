package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class TokenConfig {

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

//    @Bean
//    public TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 设置 JWT 的签名密钥。签名密钥用于对 JWT 进行签名和验证，确保令牌的完整性和安全性
        converter.setSigningKey("your-signing-key");
        // 设置用于访问令牌和 JWT 之间的转换的AccessTokenConverter实例
        // AccessTokenConverter负责在访问令牌和JWT之间进行属性的映射和转换
        converter.setAccessTokenConverter(new DefaultAccessTokenConverter());
        // 使用 RedisTokenStore 解析令牌
//        converter.setJwtClaimsSetVerifier(redisTokenStore());
        return converter;
    }

//    @Bean
//    public DefaultAccessTokenConverter defaultAccessTokenConverter(){
//
//    }

}
