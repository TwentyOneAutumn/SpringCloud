package com.server.join.domain.entity;

import cn.hutool.http.HttpStatus;
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
        return new Result(HttpStatus.HTTP_OK, "操作成功");
    }

    protected static Result success(String msg) {
        return new Result(HttpStatus.HTTP_OK, msg);
    }

    protected static Result error() {
        return new Result(HttpStatus.HTTP_INTERNAL_ERROR, "操作失败");
    }

    protected static Result error(String msg) {
        return new Result(HttpStatus.HTTP_INTERNAL_ERROR, msg);
    }

    protected static Result error(int code, String msg) {
        return new Result(code, msg);
    }


    /**
     * 是否成功
     * @return true:成功 false:失败
     */
    public boolean isSuccess(){
        return code > 199 && code < 300;
    }



    /**
     * 判断请求是否成功
     * @param msg 错误信息
     */
    public void isError(String msg){
        if(!isSuccess()){
            throw new RuntimeException(msg);
        }
    }
}
