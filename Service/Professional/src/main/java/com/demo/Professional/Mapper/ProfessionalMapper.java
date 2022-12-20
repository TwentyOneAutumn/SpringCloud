package com.demo.Professional.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.Professional.DoMain.Dto.ProfessionalByAddressDto;
import com.demo.Professional.DoMain.Professional;
import com.demo.Professional.DoMain.Vo.ProfessionalByAddressVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProfessionalMapper extends BaseMapper<Professional> {
    List<ProfessionalByAddressVo> getProfessionalListByAddress(@Param("obj") ProfessionalByAddressDto dto);
}
