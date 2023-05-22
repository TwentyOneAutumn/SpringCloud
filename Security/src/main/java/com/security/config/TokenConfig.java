package com.security.config;

import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import com.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@ComponentScan(basePackages = {"com.security"})
public class TokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;



    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(HttpServletResponse response) {
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

    public void writerToken(){
        String clientId = null;
        String clientSecret = null;
        String scope = null;
        String userCode = null;
        String password = null;
        ClientDetails clientDetails = null;
        TokenRequest tokenRequest = new TokenRequest(new HashMap<String,String>(),clientId,clientDetails.getScope(),"");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCode, password));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authenticate);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        oAuth2Authentication.setAuthenticated(true);
        String accessToken = oAuth2AccessToken.getValue();
        String refreshToken = oAuth2AccessToken.getRefreshToken().toString();
        String tokenType = oAuth2AccessToken.getTokenType();
        int expiresIn = oAuth2AccessToken.getExpiresIn();
        RequestUtils.writer(response, Build.buildRow(oAuth2AccessToken));
    }

    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);
        // 支持刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // 设置令牌存储策略
        tokenServices.setTokenStore(tokenStore);
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(null);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        return tokenServices;
    }

    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }
}
