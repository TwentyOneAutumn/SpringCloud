package com.demo.test.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends Base{
    /**
     * 项目ID
     */
    private String itemId;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目价格
     */
    private String itemPrice;

    /**
     * 项目编号
     */
    private String itemNumber;

    /**
     * 排序ID
     */
    private Integer IsOrder;
}
