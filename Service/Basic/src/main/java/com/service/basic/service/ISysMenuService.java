package com.service.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.doMain.*;
import com.service.basic.doMain.dto.*;
import com.service.basic.doMain.vo.*;

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
     * @return AjaxResult
     */
    AjaxResult toAdd(SysMenuAddDto dto);


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toEdit(SysMenuEditDto dto);


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toDelete(SysMenuDeleteDto dto);
}
