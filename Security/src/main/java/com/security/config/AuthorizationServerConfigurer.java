package com.security.config;

import com.security.doMain.SecurityUserDetailsService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private TokenEnhancer tokenEnhancer;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 设置用于处理认证请求的认证管理器，该方法将指定的认证管理器用于验证用户身份
                .authenticationManager(authenticationManager)
                // 置令牌存储方式，通过该方法指定的令牌存储将用于存储生成的访问令牌
                .tokenStore(tokenStore)
                // 配置自定义UserDetailsService
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 配置客户端的详情信息存储在内存中
                .inMemory()
                // 指定客户端的密码
                .withClient("your-client-id")
                // 指定客户端的密码
                .secret("your-client-secret")
                // 指定客户端授权的授权类型，其中包括密码授权模式和刷新令牌模式
                .authorizedGrantTypes("password", "refresh_token")
                // 指定客户端的访问范围
                .scopes("read", "write")
                // 指定访问令牌的有效期（单位：秒）
                .accessTokenValiditySeconds(3600)
                // 指定访问令牌的有效期（单位：秒）
                .refreshTokenValiditySeconds(86400);
    }
}
