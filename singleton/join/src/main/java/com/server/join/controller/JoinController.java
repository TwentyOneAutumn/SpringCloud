package com.server.join.controller;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.server.join.domain.Address;
import com.server.join.domain.User;
import com.server.join.domain.entity.Build;
import com.server.join.domain.entity.TableInfo;
import com.server.join.domain.vo.UserListVo;
import com.server.join.service.IAddressService;
import com.server.join.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping
@RestController
public class JoinController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IUserService userService;


    @GetMapping("query")
    public TableInfo<UserListVo> query() {
        List<UserListVo> list = userService.selectJoinList(UserListVo.class,
                new MPJLambdaWrapper<User>()
                        .selectAll(User.class)
                        .select(Address::getCity,Address::getAddress)
                        .leftJoin(Address.class,Address::getUserId,User::getId)
                        .eq(User::getId,1)
        );
        return Build.table(list);
    }
}
