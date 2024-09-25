package com.service.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.domain.*;
import com.service.basic.domain.SysMenu;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.*;

/**
 * 菜单Service
 */
public interface ISysMenuService extends IService<SysMenu> {


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    TableInfo<SysMenuListVo> toList(SysMenuListDto dto);


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    Row<SysMenuDetailVo> toDetail(SysMenuDetailDto dto);


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    Result toAdd(SysMenuAddDto dto);


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    Result toEdit(SysMenuEditDto dto);


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    Result toDelete(SysMenuDeleteDto dto);
}
