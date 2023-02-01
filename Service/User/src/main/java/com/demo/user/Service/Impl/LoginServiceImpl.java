package com.demo.user.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.Core.DoMain.AjaxResult;
import com.demo.Core.Utils.JwtUtil;
import com.demo.user.DoMain.Dto.LoginGetUserDto;
import com.demo.user.DoMain.Dto.LoginUserDto;
import com.demo.user.DoMain.User;
import com.demo.user.DoMain.Vo.LoginGetUserVo;
import com.demo.user.Mapper.UserMapper;
import com.demo.user.Service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceImpl  extends ServiceImpl<UserMapper, User> implements LoginService {

    @Autowired
    RedisTemplate<String,Object> redisClient;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 用户登录认证
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult login(LoginUserDto dto, HttpServletRequest http){
        String userId = dto.getUserId();
        String passWord = dto.getPassWord();
        // 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userId,
                passWord
        );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证失败,抛出异常
        if (BeanUtil.isEmpty(authenticate)) {
            log.error("用户登录认证失败");
            throw  new RuntimeException("用户登录认证失败");
        }
        // 获取用户信息
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authenticate.getPrincipal();

        // 获取请求IP
        String ip = http.getHeaders("IP").nextElement();
        // 判断IP是否存在Token
        String token = (String)redisClient.opsForValue().get(ip);
        if(StringUtils.isEmpty(token)){
            // 获取Token
            token = JwtUtil.getToken(userId + passWord, userId, passWord);
            // 存储Token,并设置过期时间1小时
            redisClient.opsForValue().set(ip,token,1, TimeUnit.HOURS);
            log.info("{ID:" + userId + ",IP:" + ip + ",Token:" + token + "}");
        }
        // 设置map集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", JwtUtil.getToken(userId + passWord, userId, passWord));
        User userInfo = getById(dto.getUserId());
        LoginGetUserVo vo = BeanUtil.copyProperties(userInfo, LoginGetUserVo.class);
        map.put("user",vo);
        // 返回AjaxResult
        return AjaxResult.success(map);
    }

    /**
     * 获取用户信息
     * @return AjaxResult
     */
    @Override
    public AjaxResult getUser(LoginGetUserDto dto) {
        User user = getById(dto.getUserId());
        // 判空
        if(BeanUtil.isEmpty(user)){
            return AjaxResult.error("该用户不存在");
        }
        LoginGetUserVo vo = BeanUtil.copyProperties(user, LoginGetUserVo.class);
        return AjaxResult.success(vo);
    }
}
