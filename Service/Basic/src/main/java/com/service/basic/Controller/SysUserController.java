package com.service.basic.Controller;

import com.demo.Core.DoMain.AjaxResult;
import com.demo.Core.DoMain.Row;
import com.demo.Core.DoMain.TableInfo;
import com.service.basic.DoMain.Dto.*;
import com.service.basic.DoMain.Vo.SysUserDetailVo;
import com.service.basic.DoMain.Vo.SysUserListVo;
import com.service.basic.Service.ISysUserService;
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
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @GetMapping("/list")
    public TableInfo<SysUserListVo> toList(@Valid SysUserListDto dto){
        return sysUserService.toList(dto);
    }


    /**
     * 明细
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/detail")
    public Row<SysUserDetailVo> toDetail(@Valid SysUserDetailDto dto){
        return sysUserService.toDetail(dto);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PostMapping("/add")
    public AjaxResult toAdd(@Valid @RequestBody SysUserAddDto dto){
        return sysUserService.toAdd(dto);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PutMapping("/edit")
    public AjaxResult toEdit(@Valid @RequestBody SysUserEditDto dto){
        return sysUserService.toEdit(dto);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @DeleteMapping("/delete")
    public AjaxResult toDelete(@Valid @RequestBody SysUserDeleteDto dto){
        return sysUserService.toDelete(dto);
    }
}
