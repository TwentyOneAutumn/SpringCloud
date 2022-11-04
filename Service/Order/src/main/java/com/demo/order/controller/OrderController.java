package com.demo.order.controller;

import com.demo.feign.UserClient;
import com.demo.order.pojo.Order;
import com.demo.order.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

   @Autowired
   private OrderServiceImpl orderServiceImpl;

   @Autowired
   UserClient userClient;

    @GetMapping("/{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId ,
                                    @RequestHeader String HeaderMessage) {
        // 根据id查询订单
        Order order = orderServiceImpl.getById(orderId);
        // 调用Feign查询对应User信息并填充到Order对象
        order.setUser(userClient.queryById(order.getUserId()));
        return order;
    }

    @GetMapping("/queryAll")
    public List<Order> queryAllIsOrder() {
        List<Order> orderList = orderServiceImpl.list();
        for (Order order : orderList) {
            order.setUser(userClient.queryById(order.getUserId()));
        }
        return orderList;
    }
}
