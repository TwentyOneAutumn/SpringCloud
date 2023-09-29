package com.service.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.doMain.basic.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
