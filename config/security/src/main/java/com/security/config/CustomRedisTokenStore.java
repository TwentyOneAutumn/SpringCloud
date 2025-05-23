//package com.security.config;
//
//import cn.hutool.core.collection.CollUtil;
//import com.security.enums.RedisTokenKey;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.Cursor;
//import org.springframework.data.redis.core.ScanOptions;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * 自定义实现 RedisTokenStore
// * 实现单点登录
// */
//@Component
//public class CustomRedisTokenStore extends RedisTokenStore {
//
//    /**
//     * Redis连接工厂对象
//     */
//    private final RedisConnectionFactory factory;
//
//    /**
//     * 序列化对象
//     */
//    private final RedisTokenStoreSerializationStrategy strategy = new JdkSerializationStrategy();
//
//    /**
//     * 构造方法
//     * @param factory Redis连接工厂对象
//     */
//    public CustomRedisTokenStore(RedisConnectionFactory factory) {
//        super(factory);
//        this.factory = factory;
//    }
//
//    /**
//     * 用于将访问令牌（access token）和与之关联的身份验证信息（authentication）存储到Redis
//     * @param token Token
//     * @param authentication 身份信息对象
//     */
//    @Override
//    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
//        // 在存储访问令牌时，先删除之前相同用户的访问令牌
//        removeAccessTokenByUserName(authentication);
//        // 调用父类的存储方法存储新的访问令牌
//        super.storeAccessToken(token, authentication);
//    }
//
//    /**
//     * 在存储访问令牌时，先删除之前相同用户,客户端,IP的访问令牌
//     * @param authentication 身份信息对象
//     */
//    private void removeAccessTokenByUserName(OAuth2Authentication authentication) {
//        String userName = authentication.getName();
//        String patternKey = RedisTokenKey.USER_NAME + "#" + userName;
//        // 通过键的前缀匹配，找到所有与用户名相关的访问令牌
//        byte[] pattern = strategy.serialize(patternKey);
//        Set<byte[]> keysToDelete = new HashSet<>();
//        // 获取Redis连接
//        RedisConnection conn = factory.getConnection();
//        try {
//            // 通过正则匹配所有Key
//            Cursor<byte[]> cursor = conn.scan(ScanOptions.scanOptions().match(pattern).build());
//            // 迭代获取Key
//            while (cursor.hasNext()) {
//                byte[] key = cursor.next();
//                keysToDelete.add(key);
//            }
//            cursor.close();
//        } finally {
//            conn.close();
//        }
//
//        // 删除相关的访问令牌
//        if (CollUtil.isNotEmpty(keysToDelete)) {
//            conn = factory.getConnection();
//            try {
//                conn.del(keysToDelete.toArray(new byte[keysToDelete.size()][]));
//            } finally {
//                conn.close();
//            }
//        }
//    }
//}
