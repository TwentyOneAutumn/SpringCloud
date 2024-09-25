package com.service.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.basic.api.domain.UserDetailInfo;
import com.core.domain.Result;
import com.core.domain.Row;
import com.core.domain.TableInfo;
import com.service.basic.domain.SysUser;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.SysUserAddVo;
import com.service.basic.domain.vo.SysUserDetailVo;
import com.service.basic.domain.vo.SysUserListVo;

/**
 * 用户Service
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    TableInfo<SysUserListVo> toList(SysUserListDto dto) throws Exception;


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    Row<SysUserDetailVo> toDetail(SysUserDetailDto dto);

    
    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    Row<SysUserAddVo> toAdd(SysUserAddDto dto);

    
    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    Result toEdit(SysUserEditDto dto);

    
    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    Result toDelete(SysUserDeleteDto dto);


    /**
     * 获取用户详细信息及权限信息
     *
     * @param user 数据对象
     * @return Row
     */
    Row<UserDetailInfo> getUserInfo(String userCode);


    /**
     * 判断当前用户是否存在
     * @param dto 数据对象
     * @return Boolean
     */
    Result checkUser(String userCode);
}
