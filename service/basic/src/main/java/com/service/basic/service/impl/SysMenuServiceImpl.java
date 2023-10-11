package com.service.basic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.doMain.*;
import com.core.doMain.basic.SysMenu;
import com.service.basic.doMain.dto.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.service.basic.doMain.vo.SysMenuDetailVo;
import com.service.basic.doMain.vo.SysMenuListVo;
import com.service.basic.mapper.SysMenuMapper;
import com.service.basic.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    @Autowired
    private SysMenuMapper SysMenuMapper;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @Override
    public TableInfo<SysMenuListVo> toList(SysMenuListDto dto) {
        Page<Object> page = PageEntity.build(dto);
        List<SysMenu> list = list(new LambdaQueryWrapper<SysMenu>());
        List<SysMenuListVo> voList = BeanUtil.copyToList(list, SysMenuListVo.class);
        return Build.table(page,voList);
    }


    /**
     * 明细
     * @param dto 数据对象
     * @return Row
     */
    @Override
    public Row<SysMenuDetailVo> toDetail(SysMenuDetailDto dto) {
        SysMenu pojo = getById(dto.getMenuId());
        if(BeanUtil.isEmpty(pojo)){
            throw new RuntimeException("数据不存在");
        }
        SysMenuDetailVo vo = BeanUtil.toBean(pojo, SysMenuDetailVo.class);
        return Build.row(vo);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toAdd(SysMenuAddDto dto) {
        SysMenu pojo = BeanUtil.toBean(dto, SysMenu.class);
        boolean save = save(pojo);
        return Build.ajax(save);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toEdit(SysMenuEditDto dto) {
        if(BeanUtil.isEmpty(getById(dto.getMenuId()))){
            throw new RuntimeException("数据不存在");
        }
        SysMenu pojo = BeanUtil.toBean(dto, SysMenu.class);
        boolean update = updateById(pojo);
        return update ? AjaxResult.success() : AjaxResult.error();
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toDelete(SysMenuDeleteDto dto) {
        String id = dto.getMenuId();
        if(BeanUtil.isEmpty(getById(id))){
            throw new RuntimeException("数据不存在");
        }
        boolean remove = removeById(id);
        return remove ? AjaxResult.success() : AjaxResult.error();
    }
}
