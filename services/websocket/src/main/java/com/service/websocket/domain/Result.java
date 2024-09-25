package com.service.websocket.domain;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 相应状态类
 */
@Data
public class Result implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    protected Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    protected static Result success() {
        return new Result(HttpStatus.SUCCESS, "操作成功");
    }

    protected static Result success(String msg) {
        return new Result(HttpStatus.SUCCESS, msg);
    }

    protected static Result error() {
        return new Result(HttpStatus.ERROR, "操作失败");
    }

    protected static Result error(String msg) {
        return new Result(HttpStatus.ERROR, msg);
    }

    protected static Result error(int code, String msg) {
        return new Result(code, msg);
    }


    /**
     * 是否成功
     * @param result 数据对象
     * @return true:成功 false:失败
     */
    public static boolean isSuccess(Result result){
        return BeanUtil.isNotEmpty(result) && result.getCode() > 199 && result.getCode() < 300;
    }


    /**
     * 是否失败
     * @param result 数据对象
     * @return true:失败 false:成功
     */
    public static boolean isError(Result result){
        return !isSuccess(result);
    }
}
