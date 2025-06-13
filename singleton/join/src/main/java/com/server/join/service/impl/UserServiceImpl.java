package com.server.join.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.server.join.domain.User;
import com.server.join.mapper.UserMapper;
import com.server.join.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, User> implements IUserService {
}
