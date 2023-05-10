package com.demo.Core.DoMain;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * 表单数据封装
 * @param <T> 集合数据类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo<T> implements Serializable {
    /**
     * 记录数
     */
    private int total;

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private Collection<T> collection;

    public TableInfo(Collection<T> collection){
        this.total = CollUtil.isNotEmpty(collection) ? collection.size() : 0;
        this.code = HttpStatus.SUCCESS;
        this.msg = "操作成功";
        this.collection = collection;
    }

    public TableInfo(int total,Collection<T> collection){
        this.total = total;
        this.code = HttpStatus.SUCCESS;
        this.msg = "操作成功";
        this.collection = collection;
    }
}
