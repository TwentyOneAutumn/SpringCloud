package com.server.join.domain.entity;

import cn.hutool.http.HttpStatus;

import java.util.Collection;

/**
 * 响应对象构建类
 */
public class Build {

    /**
     * 构建TableInfo
     * @param collection 集合
     * @return TableInfo
     */
    public static  <T> TableInfo<T> table(Collection<T> collection){
        return new TableInfo<>(collection);
    }


    /**
     * 构建TableInfo
     * @param collection 集合
     * @return TableInfo
     */
    public static  <T> TableInfo<T> table(Collection<T> collection,long total){
        return new TableInfo<>(total,collection);
    }


    /**
     * 构建TableInfo
     * @param msg 错误信息
     * @return TableInfo
     */
    public static  <T> TableInfo<T> table(String msg){
        return new TableInfo<>(0, HttpStatus.HTTP_OK,msg,null);
    }


    /**
     * 构建Row
     * @param row 数据
     * @param <T> 泛型
     * @return Row
     */
    public static <T> Row<T> row(T row){
        return new Row<>(HttpStatus.HTTP_OK, "操作成功", row);
    }


    /**
     * 构建Row
     * @param flg 是否成功
     * @param <T> 泛型
     * @return Row
     */
    public static <T> Row<T> row(boolean flg){
        return flg ? new Row<T>(HttpStatus.HTTP_OK,"操作成功",null) : new Row<T>(HttpStatus.HTTP_INTERNAL_ERROR,"操作失败",null);
    }


    /**
     * 构建Row
     * @param flg 是否成功
     * @param <T> 泛型
     * @return Row
     */
    public static <T> Row<T> row(boolean flg, String msg){
        return flg ? new Row<T>(HttpStatus.HTTP_OK,msg,null) : new Row<T>(HttpStatus.HTTP_INTERNAL_ERROR,msg,null);
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
