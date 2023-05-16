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
        // 设置签名密钥
        converter.setSigningKey("your-signing-key");
        converter.setAccessTokenConverter(new DefaultAccessTokenConverter());
        // 使用 RedisTokenStore 解析令牌
//        converter.setJwtClaimsSetVerifier(redisTokenStore());
        return converter;
    }

}
