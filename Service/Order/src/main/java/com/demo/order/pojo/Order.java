package com.demo.order.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_order")
public class Order {
    private Long id;
    private Long price;
    private String name;
    private Integer num;
    private Long userId;
    @TableField(select = false)
    private User user;
}