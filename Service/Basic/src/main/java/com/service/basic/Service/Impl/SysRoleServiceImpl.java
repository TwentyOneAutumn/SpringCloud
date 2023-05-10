package com.service.basic.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.Core.DoMain.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.service.basic.DoMain.Dto.*;
import com.service.basic.DoMain.Vo.*;
import com.service.basic.Mapper.SysRoleMapper;
import com.service.basic.Service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {


    @Autowired
    private SysRoleMapper SysRoleMapper;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @Override
    public TableInfo<SysRoleListVo> toList(SysRoleListDto dto) {
        Page<Object> page = PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysRole> list = list(new LambdaQueryWrapper<SysRole>());
        List<SysRoleListVo> voList = BeanUtil.copyToList(list, SysRoleListVo.class);
        return Build.buildTable(page,voList);
    }


    @Override
    public Row<SysRoleDetailVo> toDetail(SysRoleDetailDto dto) {
        SysRole pojo = getById(dto.getRoleId());
        if(BeanUtil.isEmpty(pojo)){
            throw new RuntimeException("数据不存在");
        }
        SysRoleDetailVo vo = BeanUtil.toBean(pojo, SysRoleDetailVo.class);
        return Build.buildRow(vo);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toAdd(SysRoleAddDto dto) {
        SysRole pojo = BeanUtil.toBean(dto, SysRole.class);
        boolean save = save(pojo);
        return save ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toEdit(SysRoleEditDto dto) {
        if(BeanUtil.isEmpty(getById(dto.getRoleId()))){
            throw new RuntimeException("数据不存在");
        }
        SysRole pojo = BeanUtil.toBean(dto, SysRole.class);
        boolean update = updateById(pojo);
        return update ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toDelete(SysRoleDeleteDto dto) {
        String id = dto.getRoleId();
        if(BeanUtil.isEmpty(getById(id))){
            throw new RuntimeException("数据不存在");
        }
        boolean remove = removeById(id);
        return remove ? AjaxResult.success() : AjaxResult.error();
    }
}
