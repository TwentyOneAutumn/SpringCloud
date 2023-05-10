package com.service.basic.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.Core.DoMain.AjaxResult;
import com.demo.Core.DoMain.Row;
import com.demo.Core.DoMain.SysRole;
import com.demo.Core.DoMain.TableInfo;
import com.service.basic.DoMain.Dto.*;
import com.service.basic.DoMain.Vo.SysRoleDetailVo;
import com.service.basic.DoMain.Vo.SysRoleListVo;

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
     * @return AjaxResult
     */
    Row<SysRoleDetailVo> toDetail(SysRoleDetailDto dto);


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toAdd(SysRoleAddDto dto);


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toEdit(SysRoleEditDto dto);


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    AjaxResult toDelete(SysRoleDeleteDto dto);
}
