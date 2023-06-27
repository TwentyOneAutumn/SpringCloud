package com.service.basic.controller;

import com.basic.api.doMain.UserInfo;
import com.core.doMain.*;
import com.service.basic.doMain.dto.*;
import com.service.basic.doMain.vo.SysUserAddVo;
import com.service.basic.doMain.vo.SysUserDetailVo;
import com.service.basic.doMain.vo.SysUserListVo;
import com.service.basic.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 用户Controller
 */
@Slf4j
@RestController
@RequestMapping("/user")
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
     * @return Row
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
    public Row<SysUserAddVo> toAdd(@Valid @RequestBody SysUserAddDto dto){
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


    /**
     * 获取用户详细信息及权限信息
     * @param user 数据对象
     * @return Row
     */
    @PostMapping("/info")
    Row<UserInfo> getUserInfo(@RequestBody SysUser user){
        return sysUserService.getUserInfo(user);
    }


    /**
     * 判断当前用户是否存在
     * @param user 数据对象
     * @return Boolean
     */
    @PostMapping("/check")
    Row<Boolean> checkUser(@RequestBody SysUser user){
        return sysUserService.checkUser(user);
    }


    /**
     * 获取Token
     * @param user 数据对象
     * @return Boolean
     */
    @PostMapping("/token")
    Row<Boolean> toToken(@RequestBody SysUser user){
        return sysUserService.checkUser(user);
    }
}
