package com.service.basic.controller;

import com.core.domain.Result;
import com.core.domain.Row;
import com.core.domain.TableInfo;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.SysModuleDetailVo;
import com.service.basic.domain.vo.SysModuleListVo;
import com.service.basic.service.ISysModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 菜单Controller
 */
@Slf4j
@RestController
@RequestMapping("/Module")
public class SysModuleController {

    @Autowired
    private ISysModuleService sysModuleService;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @GetMapping("/list")
    public TableInfo<SysModuleListVo> toList(@Valid SysModuleListDto dto){
        return sysModuleService.toList(dto);
    }


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    @GetMapping("/detail")
    public Row<SysModuleDetailVo> toDetail(@Valid SysModuleDetailDto dto){
        return sysModuleService.toDetail(dto);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    @PostMapping("/add")
    public Result toAdd(@Valid @RequestBody SysModuleAddDto dto){
        return sysModuleService.toAdd(dto);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @PutMapping("/edit")
    public Result toEdit(@Valid @RequestBody SysModuleEditDto dto){
        return sysModuleService.toEdit(dto);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @DeleteMapping("/delete")
    public Result toDelete(@Valid @RequestBody SysModuleDeleteDto dto){
        return sysModuleService.toDelete(dto);
    }
}
