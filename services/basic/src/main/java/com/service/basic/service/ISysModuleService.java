package com.service.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.domain.Result;
import com.core.domain.Row;
import com.service.basic.domain.SysModule;
import com.core.domain.TableInfo;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.SysModuleDetailVo;
import com.service.basic.domain.vo.SysModuleListVo;

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
     * @return Result
     */
    Result toAdd(SysModuleAddDto dto);


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    Result toEdit(SysModuleEditDto dto);


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    Result toDelete(SysModuleDeleteDto dto);
}
