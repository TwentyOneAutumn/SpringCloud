package com.service.basic.controller;

import com.core.doMain.*;
import com.service.basic.doMain.Dto.*;
import com.service.basic.doMain.Vo.*;
import com.service.basic.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 用户Controller
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @GetMapping("/list")
    public TableInfo<SysRoleListVo> toList(@Valid SysRoleListDto dto){
        return sysRoleService.toList(dto);
    }


    /**
     * 明细
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/detail")
    public Row<SysRoleDetailVo> toDetail(@Valid SysRoleDetailDto dto){
        return sysRoleService.toDetail(dto);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PostMapping("/add")
    public AjaxResult toAdd(@Valid @RequestBody SysRoleAddDto dto){
        return sysRoleService.toAdd(dto);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PutMapping("/edit")
    public AjaxResult toEdit(@Valid @RequestBody SysRoleEditDto dto){
        return sysRoleService.toEdit(dto);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @DeleteMapping("/delete")
    public AjaxResult toDelete(@Valid @RequestBody SysRoleDeleteDto dto){
        return sysRoleService.toDelete(dto);
    }
}
