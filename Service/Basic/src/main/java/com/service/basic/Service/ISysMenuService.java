package com.service.basic.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.Core.DoMain.*;
import com.service.basic.DoMain.Vo.*;
import com.service.basic.DoMain.Dto.*;

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
     * @return AjaxResult
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
