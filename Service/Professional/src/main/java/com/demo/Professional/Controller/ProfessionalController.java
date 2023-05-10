package com.demo.Professional.Controller;

import com.Core.DoMain.AjaxResult;
import com.demo.Professional.DoMain.Dto.ProfessionalByAddressDto;
import com.demo.Professional.Service.Impl.ProfessionalServiceImpl;
import com.demo.Professional.DoMain.Dto.ProfessionalByScoreDto;
import com.demo.professional.api.DoMain.Vo.ProfessionalListBySchoolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class ProfessionalController {

    @Autowired
    ProfessionalServiceImpl professionalService;

    /**
     * 根据院校ID获取专业列表
     * @param schoolId 院校ID
     * @return 专业列表
     */
    @PostMapping("/getProfessionalListBySchoolId")
    List<ProfessionalListBySchoolVo> getProfessionalListBySchoolId(@RequestBody String schoolId){
        return professionalService.getProfessionalListBySchoolId(schoolId);
    }

    /**
     * 根据分数匹配专业
     * @param dto 数据对象
     * @return 专业列表
     */
    @GetMapping("/getProfessionalListByScore")
    AjaxResult getProfessionalListByScore(@Valid ProfessionalByScoreDto dto){
        return professionalService.getProfessionalListByScore(dto);
    }

    /**
     * 根据地区匹配专业
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/getProfessionalListByAddress")
    AjaxResult getProfessionalListByAddress(@Valid ProfessionalByAddressDto dto){
        return professionalService.getProfessionalListByAddress(dto);
    }

}
