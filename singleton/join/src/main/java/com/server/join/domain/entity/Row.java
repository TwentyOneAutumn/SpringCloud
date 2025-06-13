package com.server.join.domain.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
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

    protected Row(int code, String msg, T row) {
        this.code = code;
        this.msg = msg;
        this.row = row;
    }

    protected Row(T row){
        this.code = HttpStatus.HTTP_OK;
        this.msg = "操作成功";
        this.row = row;
    }

    /**
     * 获取数据
     * @param auth 是否验证数据为空
     * @param msg 错误信息
     * @return 数据
     */
    public T data(boolean auth, String msg){
        if(code > 199 && code < 300){
            if(!(auth && BeanUtil.isEmpty(row))){
                return row;
            }
        }
        throw new RuntimeException(msg);
    }

    /**
     * 获取数据
     * @param auth 是否验证数据为空
     * @param exception 异常对象
     * @return 数据
     */
    public <E extends Exception> T data(boolean auth, E exception) throws E {
        if(code > 199 && code < 300){
            if(!(auth && BeanUtil.isEmpty(row))){
                return row;
            }
        }
        throw exception;
    }

    /**
     * 是否成功
     */
    public boolean isSuccess(){
        return code > 199 && code < 300;
    }

    /**
     * 判断请求是否失败，如果失败则抛出异常
     * @param msg 错误信息
     */
    public void isError(String msg){
        if(!isSuccess()){
            throw new RuntimeException(msg);
        }
    }
}