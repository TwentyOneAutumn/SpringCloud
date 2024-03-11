package com.docker.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 相应状态类
 */
@Data
public class AjaxResult implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String msg;

    protected AjaxResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    protected static AjaxResult success() {
        return new AjaxResult(HttpStatus.SUCCESS, "操作成功");
    }

    protected static AjaxResult success(String msg) {
        return new AjaxResult(HttpStatus.SUCCESS, msg);
    }

    protected static AjaxResult error() {
        return new AjaxResult(HttpStatus.ERROR, "操作失败");
    }

    protected static AjaxResult error(String msg) {
        return new AjaxResult(HttpStatus.ERROR, msg);
    }

    protected static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg);
    }
}
