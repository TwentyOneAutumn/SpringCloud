package com.demo.Professional.Service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.Common.DoMain.AjaxResult;
import com.demo.Professional.DoMain.Dto.ProfessionalByAddressDto;
import com.demo.Professional.DoMain.Professional;
import com.demo.Professional.DoMain.Vo.ProfessionalByAddressVo;
import com.demo.Professional.Mapper.ProfessionalMapper;
import com.demo.Professional.Service.IProfessionalService;
import com.demo.Professional.DoMain.Dto.ProfessionalByScoreDto;
import com.demo.Professional.DoMain.Vo.ProfessionalByScoreVo;
import com.demo.professional.api.DoMain.Vo.ProfessionalListBySchoolVo;
import com.school.api.DoMain.SchoolDataVo;
import com.school.api.RemoteSchoolService;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalServiceImpl extends ServiceImpl<ProfessionalMapper, Professional> implements IProfessionalService {

    @Autowired
    RemoteSchoolService remoteSchoolService;

    @Autowired
    ProfessionalMapper professionalMapper;

    /**
     * 根据院校ID获取专业列表
     * @param schoolId 院校ID
     * @return 专业列表
     */
    @Override
    public List<ProfessionalListBySchoolVo> getProfessionalListBySchoolId(String schoolId) {
        // 查询专业列表
        List<Professional> list = list(new LambdaQueryWrapper<Professional>()
                .eq(Professional::getSchoolId,schoolId)
                .orderByAsc(Professional::getOrderId)
        );
        // 封装Vo
        List<ProfessionalListBySchoolVo> voList = list.stream()
                .map(v -> BeanUtil.copyProperties(v, ProfessionalListBySchoolVo.class))
                .collect(Collectors.toList());
        return voList;
    }

    /**
     * 根据分数匹配专业
     * @param dto 数据对象
     * @return 专业列表
     */
    @Override
    public AjaxResult getProfessionalListByScore(ProfessionalByScoreDto dto) {
        LambdaQueryWrapper<Professional> wrapper = new LambdaQueryWrapper<Professional>()
                .le(Professional::getLowestMark,dto.getScore());
        // 设置排序规则
        if(dto.isSortFlg()){
            wrapper = wrapper.orderByAsc(Professional::getLowestMark);
        }
        else {
            wrapper = wrapper.orderByDesc(Professional::getLowestMark);
        }
        // 查询
        List<Professional> list = list(wrapper);
        // 封装Vo
        List<ProfessionalByScoreVo> voList = list.stream()
                .map(v -> {
                    // 泛型转换
                    ProfessionalByScoreVo vo = BeanUtil.copyProperties(v, ProfessionalByScoreVo.class);
                    // 远程调用 获取专业对应学校信息
                    SchoolDataVo schoolData = remoteSchoolService.getSchoolData(vo.getSchoolId());
                    // 填充属性
                    vo.setSchoolCode(schoolData.getSchoolCode());
                    vo.setSchoolName(schoolData.getSchoolName());
                    vo.setAddress(schoolData.getAddress());
                    return vo;
                })
                .collect(Collectors.toList());
        return AjaxResult.success(voList);
    }

    /**
     * 根据地区匹配专业
     * @param dto 数据对象
     * @return AjaxResult
     */
    @Override
    public AjaxResult getProfessionalListByAddress(ProfessionalByAddressDto dto) {
        List<ProfessionalByAddressVo> voList = professionalMapper.getProfessionalListByAddress(dto);
        return AjaxResult.success(voList);
    }
}
