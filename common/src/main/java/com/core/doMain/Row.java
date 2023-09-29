package com.core.doMain;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据封装
 * @param <T> 集合数据类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Row<T> implements Serializable {

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
    private T row;

    public Row(T row){
        this.code = HttpStatus.SUCCESS;
        this.msg = "操作成功";
        this.row = row;
    }


    public static  <T> Row<T> success(T row){
        return new Row<T>(HttpStatus.SUCCESS, "操作成功", row);
    }


    /**
     * 是否成功
     * @param row 数据对象
     * @param <T> 泛型
     * @return true:成功 false:失败
     */
    public static <T> boolean isSuccess(Row<T> row){
        return !BeanUtil.isEmpty(row) && row.getCode() == 200;
    }


    /**
     * 是否失败
     * @param row 数据对象
     * @param <T> 泛型
     * @return true:失败 false:成功
     */
    public static <T> boolean isError(Row<T> row){
        return BeanUtil.isEmpty(row) || row.getCode() != 200;
    }
}
