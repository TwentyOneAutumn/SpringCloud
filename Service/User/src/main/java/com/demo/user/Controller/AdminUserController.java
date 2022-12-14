package com.demo.user.Controller;

import com.demo.Common.DoMain.AjaxResult;
import com.demo.user.DoMain.Dto.AdminUserAddDto;
import com.demo.user.DoMain.Dto.AdminUserDeleteDto;
import com.demo.user.DoMain.Dto.AdminUserEditDto;
import com.demo.user.DoMain.Dto.AdminUserListDto;
import com.demo.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    UserService userService;

    /**
     * 根据是否可用查询用户信息List
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/list")
    AjaxResult toList(@Valid AdminUserListDto dto){
        return userService.adminListByIsDel(dto);
    }

    /**
     * 新增用户信息
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PostMapping("/add")
    AjaxResult add(@RequestBody @Valid AdminUserAddDto dto){
        return userService.adminAdd(dto);
    }

    /**
     * 修改用户信息
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PutMapping("/edit")
    AjaxResult edit(@RequestBody @Valid AdminUserEditDto dto){
        return userService.adminEdit(dto);
    }

    /**
     * 删除用户 逻辑删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @DeleteMapping("/delete")
    AjaxResult delete(@RequestBody @Valid AdminUserDeleteDto dto){
        return userService.adminDelete(dto);
    }

    /**
     * 恢复用户可用状态 逻辑删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @PutMapping("/getRight")
    AjaxResult getRight(@RequestBody @Valid AdminUserDeleteDto dto){
        return userService.adminGetRight(dto);
    }
}
