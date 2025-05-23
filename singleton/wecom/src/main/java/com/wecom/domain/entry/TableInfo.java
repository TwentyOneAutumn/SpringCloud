package com.wecom.domain.entry;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * 表单数据封装
 * @param <T> 集合数据类型
 */
@Data
public class TableInfo<T> implements Serializable {
    /**
     * 记录数
     */
    private long total;

    /**
     * 状态码
     */
    private long code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private Collection<T> collection;

    protected TableInfo(Collection<T> collection){
        this.total = CollUtil.isNotEmpty(collection) ? collection.size() : 0;
        this.code = HttpStatus.HTTP_OK;
        this.msg = "操作成功";
        this.collection = collection;
    }

    protected TableInfo(long total,Collection<T> collection){
        this.total = total;
        this.code = HttpStatus.HTTP_OK;
        this.msg = "操作成功";
        this.collection = collection;
    }

    public TableInfo(long total, long code, String msg, Collection<T> collection) {
        this.total = total;
        this.code = code;
        this.msg = msg;
        this.collection = collection;
    }

    /**
     * 是否成功
     * @param tableInfo 数据对象
     * @return true:成功 false:失败
     */
    public static <T> boolean isSuccess(TableInfo<T> tableInfo){
        return BeanUtil.isNotEmpty(tableInfo) && tableInfo.getCode() > 199 && tableInfo.getCode() < 300 && CollUtil.isNotEmpty(tableInfo.getCollection());
    }


    /**
     * 是否失败
     * @param tableInfo 数据对象
     * @return true:失败 false:成功
     */
    public static <T> boolean isError(TableInfo<T> tableInfo){
        return !isSuccess(tableInfo);
    }
}
