package com.service.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.doMain.*;
import com.service.basic.mapper.SysRoleMenuMapper;
import com.service.basic.mapper.SysUserMapper;
import com.service.basic.service.ISysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    private SysUserMapper sysUserMapper;


}
