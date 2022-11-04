package com.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pojo.Order;
import java.util.List;

@FeignClient("OrderDemo")
public interface OrderClient {

    /**
     * 根据ID查询Order
     * @param orderId ID
     * @return Order
     */
    @GetMapping("order/{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId);

    /**
     * 查询所有Order信息
     * @return List<Order>
     */
    @GetMapping("order/queryAll")
    public List<Order> queryAllIsOrder();
}
