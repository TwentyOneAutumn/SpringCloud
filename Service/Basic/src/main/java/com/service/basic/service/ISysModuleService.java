package com.service.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.doMain.AjaxResult;
import com.core.doMain.Row;
import com.core.doMain.basic.SysModule;
import com.core.doMain.TableInfo;
import com.service.basic.doMain.dto.*;
import com.service.basic.doMain.vo.SysModuleDetailVo;
import com.service.basic.doMain.vo.SysModuleListVo;

/**
 * 菜单Service
 */
public interface ISysModuleService extends IService<SysModule> {


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    TableInfo<SysModuleListVo> toList(SysModuleListDto dto);


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    Row<SysModuleDetailVo> toDetail(SysModuleDetailDto dto);


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toAdd(SysModuleAddDto dto);


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toEdit(SysModuleEditDto dto);


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toDelete(SysModuleDeleteDto dto);
}
