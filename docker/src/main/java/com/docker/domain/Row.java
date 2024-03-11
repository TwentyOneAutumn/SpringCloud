package com.docker.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据封装
 * @param <T> 集合数据类型
 */
@Data
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

    public Row(int code, String msg, T row) {
        this.code = code;
        this.msg = msg;
        this.row = row;
    }

    public Row(T row){
        this.code = HttpStatus.SUCCESS;
        this.msg = "操作成功";
        this.row = row;
    }

    public static <T> Row<T> success(T row){
        return new Row<>(HttpStatus.SUCCESS, "操作成功", row);
    }
}