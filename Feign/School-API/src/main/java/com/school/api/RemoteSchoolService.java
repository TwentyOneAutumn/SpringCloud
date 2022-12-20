package com.school.api;

import com.school.api.DoMain.SchoolDataVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "RemoteSchoolService",value = "School",path = "school")
public interface RemoteSchoolService {

    @PostMapping("/getSchoolData")
    SchoolDataVo getSchoolData(@RequestBody String schoolId);
}
