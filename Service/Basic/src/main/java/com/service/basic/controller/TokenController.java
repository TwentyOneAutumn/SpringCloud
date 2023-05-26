package com.service.basic.controller;

import com.core.doMain.Build;
import com.core.doMain.TableInfo;
import com.core.utils.RequestUtils;
import com.service.basic.doMain.dto.SysUserListDto;
import com.service.basic.doMain.dto.TokenDto;
import com.service.basic.doMain.vo.SysUserListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

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
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    public void writerToken(TokenDto dto,HttpServletRequest request, HttpServletResponse response){
        try {
            String userCode = dto.getUserCode();
            String password = dto.getPassword();
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(dto.getClientId());
            String clientId = clientDetails.getClientId();
            String clientSecret = clientDetails.getClientSecret();
            Set<String> scope = clientDetails.getScope();
            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),clientId,scope,"password");
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
        } catch (Exception e) {
            throw new RuntimeException("登录异常");
        }
    }
}
