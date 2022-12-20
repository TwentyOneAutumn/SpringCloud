package com.demo.professional.api.DoMain.Vo;

import lombok.Data;

@Data
public class ProfessionalListBySchoolVo {
    private String professionalId;
    private String schoolId;
    private String professionalCode;
    private String professionalName;
    private int lowestMark;
    private String orderId;
}
