package com.demo.order.service.impl;

import com.demo.order.mapper.OrderMapper;
import com.demo.order.pojo.Order;
import com.demo.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
