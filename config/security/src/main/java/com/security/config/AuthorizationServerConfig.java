//package com.security.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//
///**
// * 授权服务器配置类
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private AuthorizationCodeServices authorizationCodeServices;
//
//    @Autowired
//    private AuthorizationServerTokenServices authorizationServerTokenServices;
//
//    /**
//     * 该方法用于配置授权服务器的安全约束
//     * 您可以定义访问授权服务器端点的权限要求，例如访问令牌端点的身份验证要求、访问令牌解析端点的权限要求等
//     * 这些配置可用于保护授权服务器的端点，并限制哪些客户端可以访问特定的端点
//     * @param security a fluent configurer for security features
//     * @throws Exception 异常
//     */
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.tokenKeyAccess("permitAll")
//                .checkTokenAccess("permitAll")
//                .allowFormAuthenticationForClients();
//    }
//
//    /**
//     * 该方法用于配置客户端详情服务（ClientDetailsService）
//     * 您可以定义客户端应用程序的信息，包括客户端ID、客户端密钥、授权范围、授权类型等
//     * 客户端详情服务用于验证客户端的身份和授权请求
//     * @param clients the client details configurer
//     * @throws Exception 异常
//     */
////    @Override
////    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////        clients.withClientDetails(clientDetailsService);
////    }
//
//    /**
//     * 方法用于配置授权服务器的端点（endpoints）
//     * 您可以设置身份验证管理器（AuthenticationManager）、令牌存储（TokenStore）、令牌增强器（TokenEnhancer）、用户详细信息服务（UserDetailsService）等
//     * 通过这些配置，您可以定义授权服务器如何验证用户身份、生成和存储令牌，并提供其他相关的功能，如刷新令牌、获取用户信息等
//     * @param endpoints the endpoints configurer
//     * @throws Exception 异常
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .authorizationCodeServices(authorizationCodeServices)
//                .tokenServices(authorizationServerTokenServices)
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
//    }
//}
