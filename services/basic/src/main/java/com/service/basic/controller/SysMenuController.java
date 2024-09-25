package com.service.basic.controller;

import com.core.domain.*;
import com.service.basic.domain.vo.*;
import com.service.basic.domain.dto.*;
import com.service.basic.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 菜单Controller
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @GetMapping("/list")
    public TableInfo<SysMenuListVo> toList(@Valid SysMenuListDto dto){
        return sysMenuService.toList(dto);
    }


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    @GetMapping("/detail")
    public Row<SysMenuDetailVo> toDetail(@Valid SysMenuDetailDto dto){
        return sysMenuService.toDetail(dto);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    @PostMapping("/add")
    public Result toAdd(@Valid @RequestBody SysMenuAddDto dto){
        return sysMenuService.toAdd(dto);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @PutMapping("/edit")
    public Result toEdit(@Valid @RequestBody SysMenuEditDto dto){
        return sysMenuService.toEdit(dto);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @DeleteMapping("/delete")
    public Result toDelete(@Valid @RequestBody SysMenuDeleteDto dto){
        return sysMenuService.toDelete(dto);
    }
}
