package com.demo.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单类型
     * 1:
     * 2:
     * 3:
     */
    private Integer orderType;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 排序ID
     */
    private Integer IsOrder;
}
