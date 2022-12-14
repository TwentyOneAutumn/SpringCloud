package com.demo.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pojo.User;

@FeignClient("User")
public interface SecurityUserClient {

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户id
     * @return 用户对象
     */
    @GetMapping("/security/queryById")
    User queryById(String userId);
}
