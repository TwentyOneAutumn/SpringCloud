package com.generator.domain;

public class Build {

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
}
