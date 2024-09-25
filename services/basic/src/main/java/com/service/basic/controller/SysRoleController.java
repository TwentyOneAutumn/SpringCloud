package com.service.basic.controller;

import com.core.domain.*;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.*;
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
     * @return Row
     */
    @GetMapping("/detail")
    public Row<SysRoleDetailVo> toDetail(@Valid SysRoleDetailDto dto){
        return sysRoleService.toDetail(dto);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    @PostMapping("/add")
    public Result toAdd(@Valid @RequestBody SysRoleAddDto dto){
        return sysRoleService.toAdd(dto);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @PutMapping("/edit")
    public Result toEdit(@Valid @RequestBody SysRoleEditDto dto){
        return sysRoleService.toEdit(dto);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @DeleteMapping("/delete")
    public Result toDelete(@Valid @RequestBody SysRoleDeleteDto dto){
        return sysRoleService.toDelete(dto);
    }
}
