package com.demo.School.DoMain.Vo;

import com.demo.professional.api.DoMain.Vo.ProfessionalListBySchoolVo;
import lombok.Data;
import java.util.List;

@Data
public class SchoolDetailVo {
    private String schoolId;
    private String schoolCode;
    private String schoolName;
    private String address;
    private int orderId;
    private List<ProfessionalListBySchoolVo> professionalList;
}
