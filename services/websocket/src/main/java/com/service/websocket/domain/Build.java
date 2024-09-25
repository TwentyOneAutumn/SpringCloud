package com.service.websocket.domain;

/**
 * 响应对象构建类
 */
public class Build {

    /**
     * 构建Row
     * @param row 数据
     * @param <T> 泛型
     * @return Row
     */
    public static <T> Row<T> row(T row){
        return new Row<>(HttpStatus.SUCCESS, "操作成功", row);
    }


    /**
     * 构建Row
     * @param flg 是否成功
     * @param <T> 泛型
     * @return Row
     */
    public static <T> Row<T> row(boolean flg){
        return flg ? new Row<T>(HttpStatus.SUCCESS,"操作成功",null) : new Row<T>(HttpStatus.ERROR,"操作失败",null);
    }


    /**
     * 构建Row
     * @param flg 是否成功
     * @param <T> 泛型
     * @return Row
     */
    public static <T> Row<T> row(boolean flg, String msg){
        return flg ? new Row<T>(HttpStatus.SUCCESS,msg,null) : new Row<T>(HttpStatus.ERROR,msg,null);
    }


    /**
     * 构建AjaxResult
     * @param flg 是否成功
     * @param msg 错误信息
     * @return Result
     */
    public static Result result(boolean flg, String msg){
        return flg ? Result.success(msg) : Result.error(msg);
    }


    /**
     * 构建AjaxResult
     * @param flg 是否成功
     * @return Result
     */
    public static Result result(boolean flg){
        return flg ? Result.success("操作成功") : Result.error("操作失败");
    }


    /**
     * 构建AjaxResult
     * @param code 状态码
     * @param msg 错误信息
     * @return Result
     */
    public static Result result(int code, String msg){
        return Result.error(code,msg);
    }
}
