package com.professional.api;

import com.demo.professional.api.DoMain.Vo.ProfessionalListBySchoolVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(contextId = "RemoteProfessionalService",value = "Professional",path = "professional")
public interface RemoteProfessionalService {

    /**
     * 根据院校ID获取专业列表
     * @param schoolId 院校ID
     * @return 专业列表
     */
    @PostMapping("/getProfessionalListBySchoolId")
    List<ProfessionalListBySchoolVo> getProfessionalListBySchoolId(@RequestBody String schoolId);
}
