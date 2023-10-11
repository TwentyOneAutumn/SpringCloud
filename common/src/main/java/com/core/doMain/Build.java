package com.core.doMain;

import java.util.Collection;
import com.github.pagehelper.Page;

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
    public static  <T> TableInfo<T> table(Page<Object> page, Collection<T> collection){
        return new TableInfo<T>((int)page.getTotal(),collection);
    }


    /**
     * 构建TableInfo
     * @param msg 错误信息
     * @return TableInfo
     */
    public static  <T> TableInfo<T> table(String msg){
        return new TableInfo<>(0,HttpStatus.ERROR,msg,null);
    }


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
     * @return AjaxResult
     */
    public static AjaxResult ajax(boolean flg, String msg){
        return flg ? AjaxResult.success(msg) : AjaxResult.error(msg);
    }



    /**
     * 构建AjaxResult
     * @param flg 是否成功
     * @return AjaxResult
     */
    public static AjaxResult ajax(boolean flg){
        return flg ? AjaxResult.success("操作成功") : AjaxResult.error("操作失败");
    }


    /**
     * 构建AjaxResult
     * @param code 状态码
     * @param msg 错误信息
     * @return AjaxResult
     */
    public static AjaxResult ajax(int code, String msg){
        return AjaxResult.error(code,msg);
    }

    /**
     * 构建Stream代理对象
     * @param collection 集合，不能为空
     * @param <T> 泛型
     * @return Stream代理对象
     */
    public static <T> StreamProxy<T> stream(Collection<T> collection){
        return new StreamProxy<>(collection);
    }
}
