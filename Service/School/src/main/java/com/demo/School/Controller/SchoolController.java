package com.demo.School.Controller;

import com.Core.DoMain.AjaxResult;
import com.demo.School.DoMain.Dto.SchoolDetailDto;
import com.demo.School.DoMain.Dto.SchoolListDto;
import com.demo.School.Service.Impl.SchoolServiceImpl;
import com.school.api.DoMain.SchoolDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class SchoolController {

    @Autowired
    SchoolServiceImpl schoolService;

    /**
     * 查询所有学校 可根据地区筛选
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/toList")
    AjaxResult toList(SchoolListDto dto){
        return schoolService.toList(dto);
    }

    /**
     * 查询学校所有专业信息
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/toDetail")
    AjaxResult toDetail(SchoolDetailDto dto){
        return schoolService.toDetail(dto);
    }

    /**
     * 根据院校ID获取院校信息
     * @param schoolId 院校ID
     * @return 院校信息
     */
    @PostMapping("/getSchoolData")
    SchoolDataVo getSchoolData(@RequestBody String schoolId){
        return schoolService.getSchoolData(schoolId);
    }
}
