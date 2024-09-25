package com.service.basic.controller;

import com.basic.api.domain.UserDetailInfo;
import com.basic.api.domain.dto.UserCodeDto;
import com.core.domain.Result;
import com.core.domain.Row;
import com.core.domain.TableInfo;
import com.file.api.RemoteFileService;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.SysUserAddVo;
import com.service.basic.domain.vo.SysUserDetailVo;
import com.service.basic.domain.vo.SysUserListVo;
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

    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @GetMapping("/list")
    public TableInfo<SysUserListVo> toList(@Valid SysUserListDto dto) throws Exception {
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
     * @return Result
     */
    @PostMapping("/add")
    public Row<SysUserAddVo> toAdd(@Valid @RequestBody SysUserAddDto dto){
        return sysUserService.toAdd(dto);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @PutMapping("/edit")
    public Result toEdit(@Valid @RequestBody SysUserEditDto dto){
        return sysUserService.toEdit(dto);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @DeleteMapping("/delete")
    public Result toDelete(@Valid @RequestBody SysUserDeleteDto dto){
        return sysUserService.toDelete(dto);
    }


    /**
     * 获取用户详细信息及权限信息
     * @param user 数据对象
     * @return Row
     */
    @PostMapping("/info")
    Row<UserDetailInfo> getUserInfo(@RequestBody UserCodeDto dto){
        return sysUserService.getUserInfo(dto.getUserCode());
    }


    /**
     * 判断当前用户是否存在
     * @param dto 数据对象
     * @return Boolean
     */
    @PostMapping("/check")
    Result checkUser(@RequestBody UserCodeDto dto){
        return sysUserService.checkUser(dto.getUserCode());
    }
}
