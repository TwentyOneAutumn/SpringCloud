package com.service.basic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.domain.Build;
import com.core.domain.Result;
import com.core.domain.Row;
import com.core.domain.TableInfo;
import com.database.domain.PageEntity;
import com.github.pagehelper.Page;
import com.service.basic.domain.SysMenu;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.SysMenuDetailVo;
import com.service.basic.domain.vo.SysMenuListVo;
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
        List<SysMenu> list = list();
        List<SysMenuListVo> voList = BeanUtil.copyToList(list, SysMenuListVo.class);
        log.info(voList.toString());
        return Build.table(voList,page.getTotal());
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
     * @return Result
     */
    @Override
    public Result toAdd(SysMenuAddDto dto) {
        SysMenu pojo = BeanUtil.toBean(dto, SysMenu.class);
        boolean save = save(pojo);
        return Build.result(save);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toEdit(SysMenuEditDto dto) {
        if(BeanUtil.isEmpty(getById(dto.getMenuId()))){
            throw new RuntimeException("数据不存在");
        }
        SysMenu pojo = BeanUtil.toBean(dto, SysMenu.class);
        boolean update = updateById(pojo);
        return Build.result(update);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toDelete(SysMenuDeleteDto dto) {
        String id = dto.getMenuId();
        if(BeanUtil.isEmpty(getById(id))){
            throw new RuntimeException("数据不存在");
        }
        boolean remove = removeById(id);
        return Build.result(remove);
    }
}
