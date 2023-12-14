package com.service.basic.controller;

import com.core.doMain.Build;
import com.core.utils.IPUtil;
import com.core.utils.ResponseUtils;
import com.core.utils.ThreadCache;
import com.service.basic.doMain.dto.TokenDto;
import com.service.basic.doMain.vo.TokenVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 获得Token
     * @param dto 数据对象
     * @param response 响应对象
     */
    @PostMapping("/get")
    public void writerToken(@Valid @RequestBody TokenDto dto, HttpServletRequest request, HttpServletResponse response){
        String ip = IPUtil.getIpAddr(request);
        Map<String, String> map = new HashMap<>();
        map.put("ip",ip);
        ThreadCache.setCache(map);
        try {
            String userCode = dto.getUserCode();
            String password = dto.getPassword();
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(dto.getClientId());
            String clientId = clientDetails.getClientId();
            String clientSecret = clientDetails.getClientSecret();
            Set<String> scope = clientDetails.getScope();
            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),clientId,scope,"customer");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCode, password));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authenticate);
            OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            oAuth2Authentication.setAuthenticated(true);
            TokenVo tokenVo = new TokenVo();
            tokenVo.setAccessToken(oAuth2AccessToken.getValue());
            tokenVo.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
            tokenVo.setClientId(clientId);
            tokenVo.setIp(ip);
            tokenVo.setUserCode(userCode);
            tokenVo.setTokenType(oAuth2AccessToken.getTokenType());
            ResponseUtils.writer(response, Build.row(tokenVo));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("登录异常");
        }
    }
}
