package com.wecom.controller;

import cn.hutool.json.JSONObject;
import com.wecom.config.RequestBuild;
import com.wecom.domain.AccessToken;
import com.wecom.domain.AuthorizationInfo;
import com.wecom.domain.UserDetail;
import com.wecom.domain.UserInfo;
import com.wecom.domain.entry.Row;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.rmi.AccessException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RequestProxy {


    /**
     * 获取企业微信 AccessToken
     */
    public static final String ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww1aa7357f11453b5f&corpsecret=QEdZKvNwwfdqjkK3_hVAAyk91BchVvZbQO8jN3V6VYU";

    /**
     * 获取企业微信 用户基本信息
     */
    public static final String USER_BASE = "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo";

    /**
     * 获取企业微信 用户详情信息
     */
    public static final String USER_DETAIL = "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserdetail";

    /**
     * 获取企业微信 用户详情信息
     */
    public static final String AUTHORIZATION_TOKEN = "http://188.2.27.182/token/authorize";
//    public static final String AUTHORIZATION_TOKEN = "http://188.2.76.113:8845/authorize";

    /**
     * AccessToken
     */
    public String accessToken;


    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        accessToken = getAccessToken();
    }


    /**
     * 获取企业微信Token
     */
    public String getAccessToken() {
        // 发送请求
        String result = RequestBuild.get(ACCESS_TOKEN, null, String.class);
        // 构建对象
        AccessToken token = AccessToken.build(result);
        // 判断是否错误
        Integer errCode = token.getErrcode();
        String errMsg = token.getErrmsg();
        if(errCode != 0 || !"ok".equals(errMsg)){
            throw new RuntimeException("获取AccessToken异常:" + errMsg);
        }
        return token.getAccessToken();
    }


    /**
     * 获取企业微信用户基本信息
     */
    @Retryable(value = AccessException.class,maxAttempts = 2,backoff = @Backoff(delay = 1000))
    public UserInfo getUserInfo(String code) throws AccessException {
        // 刷新Token
        refreshAccessToken();
        // 构建参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        paramMap.put("code", code);
        // 发送请求
        String result = RequestBuild.get(USER_BASE, paramMap);
        log.info("用户基本信息:{}", result);
        // 构建对象
        UserInfo info = UserInfo.build(result);
        // 判断是否错误
        isError(info.getErrcode(),info.getErrmsg(),"获取企业微信用户基本信息异常");
        return info;
    }


    /**
     * 获取企业微信用户详情信息
     */
    @Retryable(value = AccessException.class,maxAttempts = 2,backoff = @Backoff(delay = 1000))
    public UserDetail getUserDetail(String ticket) throws AccessException {
        // 刷新Token
        refreshAccessToken();
        // 构建参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("user_ticket", ticket);
        // 发送请求
        String result = RequestBuild.post(USER_DETAIL + "?access_token=" + accessToken, paramMap);
        log.info("用户详情信息:{}", result);
        UserDetail detail = new JSONObject(result).toBean(UserDetail.class);
        // 判断是否错误
        isError(detail.getErrcode(),detail.getErrmsg(),"获取企业微信用户详情信息异常");
        return detail;
    }


    /**
     * 获取自定义AccessToken
     */
    public AuthorizationInfo authorization(String mobile) throws AccessException {
        // 发送请求
        String result =  RequestBuild.get(AUTHORIZATION_TOKEN + "?mobile=" + mobile, null);
        log.info("自定义AccessToken:{}", result);
        Row<AuthorizationInfo> row = new JSONObject(result).toBean(Row.class);
        AuthorizationInfo info = row.isSuccess(AuthorizationInfo.class,new AccessException(row.getMsg()));
        return info;
    }


    /**
     * 刷新AccessToken
     */
    public void refreshAccessToken(){
        // 获取当前重试上下文
        RetryContext context = RetrySynchronizationManager.getContext();
        int retryCount = context != null ? context.getRetryCount() : 0;
        if(retryCount == 1){
            // 重新获取AccessToken
            accessToken = getAccessToken();
            log.info("重新获取AccessToken:{}",accessToken);
        }
    }


    /**
     * 校验是否异常
     */
    public void isError(Integer errCode, String errMsg,String responseMsg) throws AccessException {
        if(errCode != 0 || !"ok".equals(errMsg)){
            // 判断Token是否过期
            if(errCode == 42001 && errMsg.contains("access_token expired")){
                throw new AccessException("AccessToken过期");
            }
            throw new RuntimeException(responseMsg + ":" + errMsg);
        }
    }
}
