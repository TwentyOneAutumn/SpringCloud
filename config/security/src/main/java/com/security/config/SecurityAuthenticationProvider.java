//package com.security.config;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
///**
// * 重写AuthenticationProvider
// * 当调用 AuthenticationManager 的 authenticate() 方法时，AuthenticationManager 会遍历配置的所有 AuthenticationProvider，依次调用它们的 authenticate() 方法进行身份验证
// * 每个 AuthenticationProvider 会根据自己的逻辑对传入的 Authentication 对象进行验证，并返回一个完整的 Authentication 对象表示验证结果
// */
//@Component
//public class SecurityAuthenticationProvider implements AuthenticationProvider {
//
//    /**
//     * 返回一个填充了身份验证结果的完整Authentication对象
//     * @param authentication Authentication对象，其中包含了用户提供的凭据信息
//     * @return 完整的Authentication对象
//     * @throws AuthenticationException 如果身份验证失败，则应抛出AuthenticationException异常
//     */
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        // 验证逻辑
//        return authentication;
//    }
//
//    /**
//     * 是否支持特定类型的身份验证请求
//     * @param authentication AuthenticationClass对象
//     * @return true:支持  false:不支持
//     */
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
