package com.demo.School.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Core.DoMain.AjaxResult;
import com.demo.School.DoMain.Dto.SchoolDetailDto;
import com.demo.School.DoMain.Dto.SchoolListDto;
import com.demo.School.DoMain.School;
import com.school.api.DoMain.SchoolDataVo;

public interface ISchoolService extends IService<School> {

    AjaxResult toList(SchoolListDto dto);

    AjaxResult toDetail(SchoolDetailDto dto);

    SchoolDataVo getSchoolData(String schoolId);
}
