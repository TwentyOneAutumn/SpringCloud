package com.service.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.doMain.*;
import com.service.basic.doMain.Dto.*;
import com.service.basic.doMain.Vo.*;

/**
 * 用户Service
 */
public interface ISysUserService extends IService<SysUser> {


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    TableInfo<SysUserListVo> toList(SysUserListDto dto);

    
    /**
     * 明细
     * @param dto 数据对象
     * @return AjaxResult
     */
    Row<SysUserDetailVo> toDetail(SysUserDetailDto dto);

    
    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toAdd(SysUserAddDto dto);

    
    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toEdit(SysUserEditDto dto);

    
    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toDelete(SysUserDeleteDto dto);
}
