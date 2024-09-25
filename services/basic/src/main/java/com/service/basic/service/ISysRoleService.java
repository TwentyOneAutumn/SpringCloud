package com.service.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.domain.*;
import com.service.basic.domain.SysRole;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.*;

public interface ISysRoleService extends IService<SysRole> {


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    TableInfo<SysRoleListVo> toList(SysRoleListDto dto);


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    Row<SysRoleDetailVo> toDetail(SysRoleDetailDto dto);


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    Result toAdd(SysRoleAddDto dto);


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    Result toEdit(SysRoleEditDto dto);


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    Result toDelete(SysRoleDeleteDto dto);
}
