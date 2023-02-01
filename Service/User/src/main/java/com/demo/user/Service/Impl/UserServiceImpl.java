package com.demo.user.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.Core.DoMain.AjaxResult;
import com.demo.user.DoMain.Dto.*;
import com.demo.user.DoMain.Vo.AdminUserListVo;
import com.demo.user.Mapper.UserMapper;
import com.demo.user.DoMain.User;
import com.demo.user.Service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户id
     * @return 用户对象
     */
    @Override
    public User SecurityQueryById(String userId) {
        return this.getById(userId);
    }

    /**
     * 根据是否可用查询用户信息List
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult adminListByIsDel(AdminUserListDto dto) {
        // 查询
        List<User> list = list(new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, dto.getIsDeleted())
        );
        // 封装VoList
        List<AdminUserListVo> voList = list.stream()
                .map(v -> BeanUtil.copyProperties(v, AdminUserListVo.class))
                .collect(Collectors.toList());
        // 返回AjaxResult
        return AjaxResult.success(voList);
    }

    /**
     * 新增用户信息
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult adminAdd(AdminUserAddDto dto) {
        User user = BeanUtil.copyProperties(dto, User.class);
        // 判断用户名称是否存在
        int count = count(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, user.getUserName())
        );
        if (count > 0) {
            return AjaxResult.error("用户名已存在");
        }
        // 新增用户信息
        boolean save = save(user);
        // 返回AjaxResult
        return save ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 修改用户信息
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult adminEdit(AdminUserEditDto dto) {
        User user = BeanUtil.copyProperties(dto, User.class);
        // 根据ID更新
        boolean update = updateById(user);
        // 返回AjaxResult
        return update ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 删除用户 逻辑删除
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult adminDelete(AdminUserDeleteDto dto) {
        User user = BeanUtil.copyProperties(dto, User.class);
        // 根据ID删除
        boolean delete = removeById(user);
        // 返回AjaxResult
        return delete ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 恢复用户可用状态 逻辑删除
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult adminGetRight(AdminUserDeleteDto dto) {
        User user = BeanUtil.copyProperties(dto, User.class);
        // 设置可用状态
        user.setIsDeleted(true);
        // 更新
        boolean update = updateById(user);
        // 返回AjaxResult
        return update ? AjaxResult.success() : AjaxResult.error();
    }
}