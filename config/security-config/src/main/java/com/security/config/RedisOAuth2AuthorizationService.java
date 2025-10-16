//package com.security.config;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.util.StrUtil;
//import com.basic.api.domain.UserInfo;
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
//import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
//import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * 负责管理 OAuth 2.0 授权相关事宜
// */
//@Slf4j
//public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {
//
//    /**
//     * Redis前缀分组
//     */
//    public static final String ACCESS_TOKEN = "access-token:";
//    public static final String USER_INFO = "user-info:";
//    public static final String TOKEN_USER = "token-user:";
//    public static final String USER_TOKEN = "user-token:";
//    public static final String AUTHORIZATION_CODE = "authorization-code:";
//    public static final String STATE = "state:";
//
//    @Value("${token.config.expires-in.access-token:#{7}}")
//    private Integer accessTokenExpiresIn;
//
//    @Value("${token.config.expires-in.authorization-code:#{7}}")
//    private Integer authorizationCodeExpiresIn;
//
//    @Value("${token.config.expires-in.state:#{7}}")
//    private Integer stateExpiresIn;
//
//
//    private final RedisTemplate<String,Object> redisTemplate;
//
//    /**
//     * 序列化工具
//     */
//    private final JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
//
//
//    /**
//     * 构造方法
//     */
//    public RedisOAuth2AuthorizationService(RedisConnectionFactory factory) {
//        // 创建RedisTemplate对象
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        // 配置连接工厂
//        redisTemplate.setConnectionFactory(factory);
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异常
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        StringRedisSerializer stringSerial = new StringRedisSerializer();
//        // 设置RedisKey的列化方式:StringRedisSerializer
//        redisTemplate.setKeySerializer(stringSerial);
//        // 设置RedisValue的列化方式:Jackson2JsonRedisSerializer
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        // 初始化
//        redisTemplate.afterPropertiesSet();
//        this.redisTemplate = redisTemplate;
//    }
//
//
//    /**
//     * 保存OAuth授权信息
//     */
//    @Override
//    @SneakyThrows
//    public void save(OAuth2Authorization authorization) {
//        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authorization.getToken(OAuth2AuthorizationCode.class);
//        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getToken(OAuth2AccessToken.class);
//        Map<String, Object> attributes = authorization.getAttributes();
//        String state = (String)attributes.get("state");
//        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)attributes.get("java.security.Principal");
//
//        // 处理AccessToken
//        if(BeanUtil.isNotEmpty(accessToken)){
//            String token = accessToken.getToken().getTokenValue();
//            storage(ACCESS_TOKEN + token,authorization,accessTokenExpiresIn);
//
//            if(BeanUtil.isNotEmpty(authorizationCodeToken)){
//                AuthDetails authDetails = (AuthDetails) authenticationToken.getPrincipal();
//                UserInfo userInfo = authDetails.getUserInfo();
//                String userCode = userInfo.getUserCode();
//
//                // Token:UserCode不存在,则存储映射
//                if(!redisTemplate.hasKey(TOKEN_USER + token)){
//                    storage(TOKEN_USER + token,userCode,accessTokenExpiresIn);
//                }
//
//                // UserCode:Token不存在,则存储映射
//                if(!redisTemplate.hasKey(USER_TOKEN + userCode)){
//                    storage(USER_TOKEN + userCode,token,accessTokenExpiresIn);
//                }
//
//                // UserCode:UserInfo不存在则存储映射
//                if(!redisTemplate.hasKey(USER_INFO + userCode)){
//                    storage(USER_INFO + userCode,authenticationToken,accessTokenExpiresIn);
//                }
//            }
//        }
//
//        // 处理授权码
//        if(BeanUtil.isNotEmpty(authorizationCodeToken)) {
//            String authorizationCode = authorizationCodeToken.getToken().getTokenValue();
//            storage(AUTHORIZATION_CODE + authorizationCode,authorization,authorizationCodeExpiresIn);
//        }
//
//        // 处理state
//        if(StrUtil.isNotBlank(state)) {
//            storage(STATE + state,authorization,stateExpiresIn);
//        }
//    }
//
//
//    /**
//     * 注销OAuth授权信息
//     */
//    @Override
//    public void remove(OAuth2Authorization authorization) {
//        Set<String> keySet = new HashSet<>();
//        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authorization.getToken(OAuth2AuthorizationCode.class);
//        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getToken(OAuth2AccessToken.class);
//        Map<String, Object> attributes = authorization.getAttributes();
//        String state = (String)attributes.get("state");
//        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)attributes.get("java.security.Principal");
//
//        // 处理AccessToken
//        if(BeanUtil.isNotEmpty(accessToken)){
//            String token = accessToken.getToken().getTokenValue();
//            keySet.add(ACCESS_TOKEN + token);
//            if(BeanUtil.isNotEmpty(authorizationCodeToken)){
//                AuthDetails authDetails = (AuthDetails) authenticationToken.getPrincipal();
//                UserInfo userInfo = authDetails.getUserInfo();
//                String userCode = userInfo.getUserCode();
//                if(redisTemplate.hasKey(TOKEN_USER + token)){
//                    keySet.add(TOKEN_USER + token);
//                }
//                if(redisTemplate.hasKey(USER_INFO + userCode)){
//                    keySet.add(USER_INFO + userCode);
//                }
//            }
//        }
//
//        // 处理授权码
//        if(BeanUtil.isNotEmpty(authorizationCodeToken)) {
//            keySet.add(AUTHORIZATION_CODE + authorizationCodeToken.getToken().getTokenValue());
//        }
//
//        // 处理state
//        if(StrUtil.isNotBlank(state)) {
//            keySet.add(STATE + state);
//        }
//
//        // 判空
//        if(CollUtil.isNotEmpty(keySet)){
//            for (String key : keySet) {
//                if(redisTemplate.hasKey(key)){
//                    redisTemplate.delete(key);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 根据ID查询OAuth授权信息
//     */
//    @Override
//    public OAuth2Authorization findById(String id) {
//        String key = null;
//        if(redisTemplate.hasKey(RedisOAuth2AuthorizationService.ACCESS_TOKEN + id)){
//            key = RedisOAuth2AuthorizationService.ACCESS_TOKEN + id;
//        }else if(redisTemplate.hasKey(RedisOAuth2AuthorizationService.AUTHORIZATION_CODE + id)){
//            key = RedisOAuth2AuthorizationService.AUTHORIZATION_CODE + id;
//
//        }else if(redisTemplate.hasKey(RedisOAuth2AuthorizationService.STATE + id)){
//            key = RedisOAuth2AuthorizationService.STATE + id;
//        }else {
//            log.error("获取上下文信息异常");
//            throw new RuntimeException("未知的Token类型:" + id);
//        }
//        byte[] bytes = (byte[])redisTemplate.opsForValue().get(key);
//        OAuth2Authorization authorization = (OAuth2Authorization)jdkSerializationRedisSerializer.deserialize(bytes);
//        return authorization;
//    }
//
//
//    /**
//     * 根据Token查询OAuth授权信息
//     */
//    @Override
//    @SneakyThrows
//    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
//        String key = null;
//        String type = tokenType.getValue();
//        if("code".equals(type)){
//            key = AUTHORIZATION_CODE + token;
//        } else if("state".equals(type)){
//            key = STATE + token;
//        } else if("access_token".equals(type)){
//            key = ACCESS_TOKEN + token;
//        } else {
//            log.error("未知的Token类型:{}",type);
//            throw new RuntimeException("未知的Token类型:" + type);
//        }
//        OAuth2Authorization authorization = deserialize(key, OAuth2Authorization.class);
//        return authorization;
//    }
//
//
//    /**
//     * 根据Token获取授权信息
//     */
//    public UsernamePasswordAuthenticationToken getToken(String token) {
//        // 判断Token是否有效
//        if(!redisTemplate.hasKey(RedisOAuth2AuthorizationService.ACCESS_TOKEN + token)){
//            throw new RuntimeException("无效的Token");
//        }
//        // 从Redis获取用户信息
//        String userCode = deserialize(RedisOAuth2AuthorizationService.TOKEN_USER + token, String.class);
//        if(StrUtil.isEmpty(userCode)){
//            throw new RuntimeException("获取Token用户映射异常");
//        }
//        UsernamePasswordAuthenticationToken authenticationToken = deserialize(RedisOAuth2AuthorizationService.USER_INFO + userCode, UsernamePasswordAuthenticationToken.class);
//        if(BeanUtil.isEmpty(authenticationToken)){
//            throw new RuntimeException("获取用户信息异常");
//        }
//        return authenticationToken;
//    }
//
//
//    /**
//     * 持久化
//     */
//    public void storage(String key,Object value,Integer expiresIn){
//        // Key不存在才存储
//        if(!redisTemplate.hasKey(key)){
//            byte[] bytes = jdkSerializationRedisSerializer.serialize(value);
//            redisTemplate.opsForValue().set(key,bytes,expiresIn, TimeUnit.DAYS);
//        }
//    }
//
//
//    /**
//     * 反序列化
//     */
//    public <T> T deserialize(String key, Class<T> clazz){
//        byte[] bytes = (byte[])redisTemplate.opsForValue().get(key);
//        return (T)jdkSerializationRedisSerializer.deserialize(bytes);
//    }
//}
