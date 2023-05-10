package com.test.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 订单类
 *
 * @author 第二十一个秋天
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends Base implements Comparable<Order> {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单类型
     * 1:新建订单
     * 2:已付款
     * 3:过期订单
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
    private Integer isOrder;

    /**
     * 类比较器，根据序号比较
     * @param o 当前对象
     * @return [大于,等于,小于] : [正数,0,负数]
     */
    @Override
    public int compareTo(Order o) {
        return this.getIsOrder() - o.getIsOrder();
    }
}