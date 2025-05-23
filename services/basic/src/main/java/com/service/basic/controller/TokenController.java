//package com.service.basic.controller;
//
//import com.core.domain.Build;
//import com.core.utils.IPUtil;
//import com.core.utils.ResponseUtils;
//import com.security.config.UserDetailsImpl;
//import com.service.basic.domain.dto.TokenDto;
//import com.service.basic.domain.vo.TokenVo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Request;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Slf4j
//@RestController
//@RequestMapping("/token")
//public class TokenController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private AuthorizationServerTokenServices authorizationServerTokenServices;
//
//    /**
//     * 获得Token
//     * @param dto 数据对象
//     * @param response 响应对象
//     */
//    @PostMapping("/get")
//    public void writerToken(@Valid @RequestBody TokenDto dto, HttpServletRequest request, HttpServletResponse response){
//        String ip = IPUtil.getIpAddr(request);
//        try {
//            String userCode = dto.getUserCode();
//            String password = dto.getPassword();
//            String clientId = dto.getClientId();
//            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCode, password));
//            UserDetailsImpl details = (UserDetailsImpl) authenticate.getPrincipal();
//            // 创建授权类型和请求参数
//            Map<String, String> requestParameters = new HashMap<>();
//            requestParameters.put("userCode", userCode);
//            requestParameters.put("clientId", clientId);
//            // 创建作用域
//            Set<String> scopes = new HashSet<>();
//            scopes.add("all");
//            OAuth2Request oauth2Request = new OAuth2Request(requestParameters, clientId, details.getAuthorities(), true, scopes, null, null, null, null);
//            SecurityContextHolder.getContext().setAuthentication(authenticate);
//            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oauth2Request, authenticate);
//            OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//            oAuth2Authentication.setAuthenticated(true);
//            TokenVo tokenVo = new TokenVo();
//            tokenVo.setAccessToken(oAuth2AccessToken.getValue());
//            tokenVo.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
//            tokenVo.setClientId(clientId);
//            tokenVo.setIp(ip);
//            tokenVo.setUserCode(userCode);
//            tokenVo.setTokenType(oAuth2AccessToken.getTokenType());
//            ResponseUtils.writer(response, Build.row(tokenVo));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("登录异常");
//        }
//    }
//}
