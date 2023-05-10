package com.service.basic.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.Core.DoMain.SysUser;
import com.service.basic.Mapper.SysUserMapper;
import com.service.basic.Service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;


}
