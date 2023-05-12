package com.service.basic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.api.doMain.UserInfo;
import com.core.doMain.*;
import com.core.utils.StreamUtils;
import com.service.basic.doMain.dto.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.service.basic.doMain.vo.SysUserDetailVo;
import com.service.basic.doMain.vo.SysUserListVo;
import com.service.basic.mapper.SysUserMapper;
import com.service.basic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Autowired
    private SysUserMapper SysUserMapper;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @Override
    public TableInfo<SysUserListVo> toList(SysUserListDto dto) {
        Page<Object> page = PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysUser> list = list(new LambdaQueryWrapper<SysUser>());
        List<SysUserListVo> voList = BeanUtil.copyToList(list, SysUserListVo.class);
        return Build.buildTable(page,voList);
    }


    @Override
    public Row<SysUserDetailVo> toDetail(SysUserDetailDto dto) {
        SysUser pojo = getById(dto.getUserId());
        if(BeanUtil.isEmpty(pojo)){
            throw new RuntimeException("数据不存在");
        }
        SysUserDetailVo vo = BeanUtil.toBean(pojo, SysUserDetailVo.class);
        return Build.buildRow(vo);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toAdd(SysUserAddDto dto) {
        SysUser pojo = BeanUtil.toBean(dto, SysUser.class);
        boolean save = save(pojo);
        return save ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toEdit(SysUserEditDto dto) {
        if(BeanUtil.isEmpty(getById(dto.getUserId()))){
            throw new RuntimeException("数据不存在");
        }
        SysUser pojo = BeanUtil.toBean(dto, SysUser.class);
        boolean update = updateById(pojo);
        return update ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toDelete(SysUserDeleteDto dto) {
        String id = dto.getUserId();
        if(BeanUtil.isEmpty(getById(id))){
            throw new RuntimeException("数据不存在");
        }
        boolean remove = removeById(id);
        return remove ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 获取用户详细信息及权限信息
     * @param user 数据对象
     * @return Row
     */
    @Override
    public Row<UserInfo> getUserInfo(SysUser user) {
        UserInfo userInfo = new UserInfo();
        // 获取用户信息
        String userId = user.getUserId();
        SysUser sysUser = getById(userId);
        userInfo.setUser(sysUser);
        // 获取该用户角色信息
        List<SysUserRole> userRoleList = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId)
        );
        if(CollUtil.isNotEmpty(userRoleList)){
            List<String> roleIdList = StreamUtils.mapToList(userRoleList, SysUserRole::getRoleId);
            List<SysRole> roleList = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                    .in(SysRole::getRoleId, roleIdList)
            );
            userInfo.setRoleList(roleList);
            // 获取该用用户菜单权限信息
            List<SysRoleMenu> roleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                    .in(SysRoleMenu::getRoleId, roleIdList)
            );
            if(CollUtil.isNotEmpty(roleMenuList)){
                List<String> menuIdList = StreamUtils.mapToList(roleMenuList, SysRoleMenu::getMenuId);
                List<SysMenu> menuList = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                        .in(SysMenu::getMenuId, menuIdList)
                );
                userInfo.setMenuList(menuList);
            }
        }
        return Build.buildRow(userInfo);
    }


    /**
     * 判断当前用户是否存在
     * @param user 数据对象
     * @return Boolean
     */
    @Override
    public Row<Boolean> checkUser(SysUser user) {
        long count = count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserCode, user.getUserCode())
        );
        return Build.buildRow(count == 1);
    }
}