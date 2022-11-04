package com.demo.user.service.impl;

import com.demo.user.mapper.UserMapper;
import com.demo.user.pojo.User;
import com.demo.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}