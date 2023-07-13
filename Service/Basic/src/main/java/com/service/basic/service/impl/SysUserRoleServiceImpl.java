package com.service.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.doMain.basic.SysUserRole;
import com.service.basic.mapper.SysUserMapper;
import com.service.basic.mapper.SysUserRoleMapper;
import com.service.basic.service.ISysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysUserMapper sysUserMapper;


}
