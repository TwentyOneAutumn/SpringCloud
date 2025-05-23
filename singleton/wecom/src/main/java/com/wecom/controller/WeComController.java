package com.wecom.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wecom.config.OMSConfig;
import com.wecom.config.RequestBuild;
import com.wecom.config.WeComConfig;
import com.wecom.domain.UserInfo;
import com.wecom.domain.dto.LoginDto;
import com.wecom.domain.entry.Build;
import com.wecom.domain.entry.Result;
import com.wecom.domain.entry.Row;
import com.wecom.domain.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/wecom")
public class WeComController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 登录验证
     */
    @Transactional
    @GetMapping("/login")
    public Result toLogin(@Valid LoginDto dto){
        String userId = dto.getUserId();

        // 判断当前用户是否已经进行登录校验
        if(!redisTemplate.hasKey(userId)){
            // 通过ID查询企业微信用户信息
            UserInfoVo userInfoVo = RequestBuild.get(WeComConfig.WECOM_USER_INFO, WeComConfig.userParam(userId), UserInfoVo.class);
            if(BeanUtil.isNotEmpty(userInfoVo.getErrcode())){
                throw new RuntimeException(userInfoVo.getErrmsg());
            }

            // 通过用户名称和手机号获取用户信息
            String userName = userInfoVo.getName();
            String mobile = userInfoVo.getMobile();
            Row<Object> row = RequestBuild.get(OMSConfig.USER_INFO, OMSConfig.userParam(userName, mobile), Row.class);
            UserInfo userInfo = row.isSuccess(UserInfo.class);
            if(BeanUtil.isEmpty(userInfo)){
                throw new RuntimeException("获取用户信息异常");
            }

            // 将用户信息序列化到Redis
            String json = new JSONObject(userInfo).toString();
            // 过期时间为2小时
            redisTemplate.opsForValue().set(userId, json, 2, TimeUnit.HOURS);
        }

        return Build.result(true);
    }

}
