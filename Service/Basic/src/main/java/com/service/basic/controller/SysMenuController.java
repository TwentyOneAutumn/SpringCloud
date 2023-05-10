package com.service.basic.controller;

import com.core.doMain.*;
import com.service.basic.doMain.Vo.*;
import com.service.basic.doMain.Dto.*;
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
     * @return AjaxResult
     */
    @GetMapping("/detail")
    public Row<SysMenuDetailVo> toDetail(@Valid SysMenuDetailDto dto){
        return sysMenuService.toDetail(dto);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PostMapping("/add")
    public AjaxResult toAdd(@Valid @RequestBody SysMenuAddDto dto){
        return sysMenuService.toAdd(dto);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PutMapping("/edit")
    public AjaxResult toEdit(@Valid @RequestBody SysMenuEditDto dto){
        return sysMenuService.toEdit(dto);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @DeleteMapping("/delete")
    public AjaxResult toDelete(@Valid @RequestBody SysMenuDeleteDto dto){
        return sysMenuService.toDelete(dto);
    }
}
