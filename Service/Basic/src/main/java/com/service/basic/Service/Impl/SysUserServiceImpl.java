package com.service.basic.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.Core.DoMain.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.service.basic.DoMain.Dto.*;
import com.service.basic.DoMain.Vo.SysUserDetailVo;
import com.service.basic.DoMain.Vo.SysUserListVo;
import com.service.basic.Mapper.SysUserMapper;
import com.service.basic.Service.ISysUserService;
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
}
