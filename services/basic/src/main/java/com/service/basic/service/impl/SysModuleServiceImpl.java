package com.service.basic.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.domain.*;
import com.database.domain.PageEntity;
import com.service.basic.domain.SysModule;
import com.github.pagehelper.Page;
import com.service.basic.domain.dto.*;
import com.service.basic.domain.vo.SysModuleDetailVo;
import com.service.basic.domain.vo.SysModuleListVo;
import com.service.basic.mapper.SysModuleMapper;
import com.service.basic.service.ISysModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户ServiceImpl
 */
@Slf4j
@Service
public class SysModuleServiceImpl extends ServiceImpl<SysModuleMapper, SysModule> implements ISysModuleService {


    @Autowired
    private SysModuleMapper SysModuleMapper;


    /**
     * 列表
     * @param dto 数据对象
     * @return TableInfo
     */
    @Override
    public TableInfo<SysModuleListVo> toList(SysModuleListDto dto) {
        Page<Object> page = PageEntity.build(dto);
        List<SysModule> list = list(new LambdaQueryWrapper<SysModule>());
        List<SysModuleListVo> voList = BeanUtil.copyToList(list, SysModuleListVo.class);
        return Build.table(voList,page.getTotal());
    }


    /**
     * 详情
     * @param dto 数据对象
     * @return Row
     */
    @Override
    public Row<SysModuleDetailVo> toDetail(SysModuleDetailDto dto) {
        SysModule pojo = getById(dto.getModuleId());
        if(BeanUtil.isEmpty(pojo)){
            throw new RuntimeException("数据不存在");
        }
        SysModuleDetailVo vo = BeanUtil.toBean(pojo, SysModuleDetailVo.class);
        return Build.row(vo);
    }


    /**
     * 新增
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toAdd(SysModuleAddDto dto) {
        SysModule pojo = BeanUtil.toBean(dto, SysModule.class);
        boolean save = save(pojo);
        return Build.result(save);
    }


    /**
     * 修改
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toEdit(SysModuleEditDto dto) {
        if(BeanUtil.isEmpty(getById(dto.getModuleId()))){
            throw new RuntimeException("数据不存在");
        }
        SysModule pojo = BeanUtil.toBean(dto, SysModule.class);
        boolean update = updateById(pojo);
        return Build.result(update);
    }


    /**
     * 删除
     * @param dto 数据对象
     * @return Result
     */
    @Override
    public Result toDelete(SysModuleDeleteDto dto) {
        String id = dto.getModuleId();
        if(BeanUtil.isEmpty(getById(id))){
            throw new RuntimeException("数据不存在");
        }
        boolean remove = removeById(id);
        return Build.result(remove);
    }
}
