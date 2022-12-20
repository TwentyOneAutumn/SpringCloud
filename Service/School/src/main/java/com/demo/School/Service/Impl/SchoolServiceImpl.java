package com.demo.School.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.Common.DoMain.AjaxResult;
import com.demo.School.DoMain.Dto.SchoolDetailDto;
import com.demo.School.DoMain.Dto.SchoolListDto;
import com.demo.School.DoMain.School;
import com.demo.School.DoMain.Vo.SchoolDetailVo;
import com.demo.School.DoMain.Vo.SchoolListVo;
import com.demo.School.Mapper.SchoolMapper;
import com.demo.School.Service.ISchoolService;
import com.demo.professional.api.DoMain.Vo.ProfessionalListBySchoolVo;
import com.professional.api.RemoteProfessionalService;
import com.school.api.DoMain.SchoolDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl  extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

    @Autowired
    RemoteProfessionalService remoteProfessionalService;

    /**
     * 查询所有学校 可根据地区筛选
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toList(SchoolListDto dto) {
        // 初始化
        LambdaQueryWrapper<School> wrapper = null;
        // 不为空的条件
        if(!StrUtil.isEmpty(dto.getAddress())){
            wrapper =  new LambdaQueryWrapper<School>()
                    .eq(School::getAddress, dto.getAddress())
                    .orderByAsc(School::getOrderId);
        }
        // 为空时的条件
        else {
            wrapper = new LambdaQueryWrapper<School>()
                    .orderByAsc(School::getOrderId);
        }
        // 查询
        List<School> list = list(wrapper);
        // 封装Vo
        List<SchoolListVo> voList = list.stream()
                .map(v -> BeanUtil.copyProperties(v, SchoolListVo.class))
                .collect(Collectors.toList());
        return AjaxResult.success(voList);
    }

    /**
     * 查询学校所有专业信息
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult toDetail(SchoolDetailDto dto) {
        // 查询
        School school = getById(dto.getSchoolId());
        // 判空
        if(BeanUtil.isEmpty(school)){
            return AjaxResult.error("数据不存在");
        }
        // 远程调用 查询专业列表
        List<ProfessionalListBySchoolVo> professionalList = remoteProfessionalService.getProfessionalListBySchoolId(dto.getSchoolId());
        // 封装Vo
        SchoolDetailVo vo = BeanUtil.copyProperties(school, SchoolDetailVo.class);
        vo.setProfessionalList(professionalList);
        return AjaxResult.success(vo);
    }

    /**
     * 根据院校ID获取院校信息
     * @param schoolId 院校ID
     * @return 院校信息
     */
    @Override
    public SchoolDataVo getSchoolData(String schoolId) {
        School school = getById(schoolId);
        return BeanUtil.copyProperties(school,SchoolDataVo.class);
    }
}
