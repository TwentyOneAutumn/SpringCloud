package com.service.basic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.basic.api.domain.MenuInfo;
import com.basic.api.domain.RoleInfo;
import com.basic.api.domain.UserDetailInfo;
import com.basic.api.domain.UserInfo;
import com.core.domain.Build;
import com.core.domain.Result;
import com.core.domain.Row;
import com.core.domain.TableInfo;
import com.database.domain.PageEntity;
import com.file.api.RemoteFileService;
import com.github.pagehelper.Page;
import com.service.basic.domain.*;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.SysUserAddVo;
import com.service.basic.domain.vo.SysUserDetailVo;
import com.service.basic.domain.vo.SysUserListVo;
import com.service.basic.mapper.SysUserMapper;
import com.service.basic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysModuleService sysModuleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RemoteFileService remoteFileService;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @Override
    public TableInfo<SysUserListVo> toList(SysUserListDto dto) throws Exception {
        Page<Object> page = PageEntity.build(dto);
        List<SysUser> list = list();
        List<SysUserListVo> voList = BeanUtil.copyToList(list, SysUserListVo.class);
        return Build.table(voList,page.getTotal());
    }


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    @Override
    public Row<SysUserDetailVo> toDetail(SysUserDetailDto dto) {
        SysUser pojo = getById(dto.getUserId());
        if(BeanUtil.isEmpty(pojo)){
            throw new RuntimeException("数据不存在");
        }
        SysUserDetailVo vo = BeanUtil.toBean(pojo, SysUserDetailVo.class);
        return Build.row(vo);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Row<SysUserAddVo> toAdd(SysUserAddDto dto) {
        SysUser pojo = BeanUtil.toBean(dto, SysUser.class);
        // 设置UserCode
        pojo.setUserCode(RandomUserCode());
        // 初始化密码
        String encode = passwordEncoder.encode(pojo.getPassword());
        pojo.setPassword(encode);
        boolean save = save(pojo);
        return save ? Build.row(BeanUtil.toBean(pojo, SysUserAddVo.class)) : Build.row(false);
    }


    /**
     * 随机生成UserCode
     * @return UserCode
     */
    public String RandomUserCode(){
        int randomInt = RandomUtil.randomInt(10000000, 99999999);
        String userCode = Integer.toString(randomInt);
        long count = count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserCode, userCode)
        );
        if(count == 0){
            return userCode;
        }else {
            return RandomUserCode();
        }
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toEdit(SysUserEditDto dto) {
        if(BeanUtil.isEmpty(getById(dto.getUserId()))){
            throw new RuntimeException("数据不存在");
        }
        SysUser pojo = BeanUtil.toBean(dto, SysUser.class);
        boolean update = updateById(pojo);
        return Build.result(update);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toDelete(SysUserDeleteDto dto) {
        String id = dto.getUserId();
        if(BeanUtil.isEmpty(getById(id))){
            throw new RuntimeException("数据不存在");
        }
        boolean remove = removeById(id);
        return Build.result(remove);
    }


    /**
     * 获取用户详细信息及权限信息
     *
     * @param user 数据对象
     * @return Row
     */
    @Override
    public Row<UserDetailInfo> getUserInfo(String userCode) {
        UserDetailInfo info = new UserDetailInfo();
        SysUser user = getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserCode,userCode)
        );
        UserInfo userInfo = BeanUtil.toBean(user, UserInfo.class);
        info.setUserInfo(userInfo);
        // 获取该用户角色信息
        List<SysUserRole> userRoleList = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, user.getUserId())
        );
        if(CollUtil.isNotEmpty(userRoleList)){
            Set<String> roleIdSet = userRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
            List<SysRole> roleList = sysRoleService.list(new LambdaQueryWrapper<SysRole>()
                    .in(SysRole::getRoleId, roleIdSet)
            );
            Set<RoleInfo> roleInfoSet = roleList.stream().map(role -> BeanUtil.toBean(role, RoleInfo.class)).collect(Collectors.toSet());
            info.setRoleInfoSet(roleInfoSet);
            // 获取该用用户菜单权限信息
            List<SysRoleMenu> roleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                    .in(SysRoleMenu::getRoleId, roleIdSet)
            );
            if(CollUtil.isNotEmpty(roleMenuList)){
                Set<String> menuIdSet = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
                List<SysMenu> menuList = sysMenuService.list(new LambdaQueryWrapper<SysMenu>()
                        .in(SysMenu::getMenuId, menuIdSet)
                );
                if(CollUtil.isNotEmpty(menuList)){
                    Set<MenuInfo> menuInfoSet = menuList.stream().map(menu -> BeanUtil.toBean(menu, MenuInfo.class)).collect(Collectors.toSet());
                    info.setMenuInfoSet(menuInfoSet);
                }
            }
        }
        return Build.row(info);
    }


    /**
     * 判断当前用户是否存在
     * @param user 数据对象
     * @return Boolean
     */
    @Override
    public Result checkUser(String userCode) {
        long count = count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserCode, userCode)
        );
        return Build.result(count == 1);
    }
}
