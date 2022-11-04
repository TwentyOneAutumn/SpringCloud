package com.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pojo.User;

@FeignClient("UserDemo")
public interface UserClient {

    /**
     * 查询Order对应User信息
     * @param id
     * @return User
     */
    @GetMapping("/user/{id}")
    User queryById(@PathVariable("id") Long id);
}
