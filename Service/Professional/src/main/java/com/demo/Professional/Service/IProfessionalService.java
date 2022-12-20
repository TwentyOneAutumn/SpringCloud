package com.demo.Professional.Service;

import com.demo.Common.DoMain.AjaxResult;
import com.demo.Professional.DoMain.Dto.ProfessionalByAddressDto;
import com.demo.Professional.DoMain.Dto.ProfessionalByScoreDto;
import com.demo.Professional.DoMain.Vo.ProfessionalByScoreVo;
import com.demo.professional.api.DoMain.Vo.ProfessionalListBySchoolVo;

import java.util.List;

public interface IProfessionalService {
    List<ProfessionalListBySchoolVo> getProfessionalListBySchoolId(String schoolId);

    AjaxResult getProfessionalListByScore(ProfessionalByScoreDto dto);

    AjaxResult getProfessionalListByAddress(ProfessionalByAddressDto dto);
}
