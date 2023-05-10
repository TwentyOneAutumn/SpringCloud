package com.test.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * 实体类父类，抽取通用属性
 */
public class Base implements Serializable {

    /**
     * 是否删除，逻辑删除，0:正常，1:删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
