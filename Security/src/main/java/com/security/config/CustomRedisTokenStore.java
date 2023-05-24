package com.security.config;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

@Component
public class CustomRedisTokenStore extends RedisTokenStore {

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    private static final String AUTH_TO_ACCESS = "auth_to_access:";


    public CustomRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        // 从给定的 OAuth2Authentication 提取键（key）
        String key = authenticationKeyGenerator.extractKey(authentication);
        // 将提取的键与前缀 AUTH_TO_ACCESS 进行拼接，并进行序列化
        byte[] serializedKey = serializeKey(AUTH_TO_ACCESS + key);
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            // 通过 RedisConnection 对象从 Redis 中获取存储的字节数组
            bytes = conn.get(serializedKey);
            if (bytes != null) {
                // 如果当前键存在，则删除当前键
                conn.del(serializedKey);
                // 获取新的键
                bytes = generateNewKey();
                serializedKey = serializeKey(AUTH_TO_ACCESS + key);
            }
        } finally {
            conn.close();
        }
        // 将获取的字节数组反序列化为 OAuth2AccessToken 对象
        OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
        if (accessToken != null) {
            // 检查存储在令牌中的 OAuth2Authentication 对象
            OAuth2Authentication storedAuthentication = readAuthentication(accessToken.getValue());
            if ((storedAuthentication == null || !key.equals(authenticationKeyGenerator.extractKey(storedAuthentication)))) {
                // 如果存储的认证信息为空或提取的键与存储的认证信息的键不相等，
                // 表示存储的认证信息已过时或已更改，需要更新存储
                storeAccessToken(accessToken, authentication);
            }
        }
        // 返回获取到的访问令牌
        return accessToken;
    }
}
