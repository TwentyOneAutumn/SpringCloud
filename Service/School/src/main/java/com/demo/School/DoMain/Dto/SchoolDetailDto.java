package com.demo.School.DoMain.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SchoolDetailDto {
    @NotBlank(message = "schoolId参数不能为空")
    private String schoolId;
}
