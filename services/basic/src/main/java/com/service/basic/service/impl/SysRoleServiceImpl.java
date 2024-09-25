package com.service.basic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.domain.*;
import com.database.domain.PageEntity;
import com.service.basic.domain.SysRole;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.*;
import com.github.pagehelper.Page;
import com.service.basic.mapper.SysRoleMapper;
import com.service.basic.service.ISysRoleService;
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
        Page<Object> page = PageEntity.build(dto);
        List<SysRole> list = list(new LambdaQueryWrapper<SysRole>());
        List<SysRoleListVo> voList = BeanUtil.copyToList(list, SysRoleListVo.class);
        return Build.table(voList,page.getTotal());
    }


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    @Override
    public Row<SysRoleDetailVo> toDetail(SysRoleDetailDto dto) {
        SysRole pojo = getById(dto.getRoleId());
        if(BeanUtil.isEmpty(pojo)){
            throw new RuntimeException("数据不存在");
        }
        SysRoleDetailVo vo = BeanUtil.toBean(pojo, SysRoleDetailVo.class);
        return Build.row(vo);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toAdd(SysRoleAddDto dto) {
        SysRole pojo = BeanUtil.toBean(dto, SysRole.class);
        boolean save = save(pojo);
        return Build.result(save);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toEdit(SysRoleEditDto dto) {
        if(BeanUtil.isEmpty(getById(dto.getRoleId()))){
            throw new RuntimeException("数据不存在");
        }
        SysRole pojo = BeanUtil.toBean(dto, SysRole.class);
        boolean update = updateById(pojo);
        return Build.result(update);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toDelete(SysRoleDeleteDto dto) {
        String id = dto.getRoleId();
        if(BeanUtil.isEmpty(getById(id))){
            throw new RuntimeException("数据不存在");
        }
        boolean remove = removeById(id);
        return Build.result(remove);
    }
}
