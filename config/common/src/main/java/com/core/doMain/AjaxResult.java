package com.core.doMain;

import cn.hutool.core.bean.BeanUtil;
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


    /**
     * 是否成功
     * @param ajaxResult 数据对象
     * @return true:成功 false:失败
     */
    public static boolean isSuccess(AjaxResult ajaxResult){
        return BeanUtil.isNotEmpty(ajaxResult) && ajaxResult.getCode() > 199 && ajaxResult.getCode() < 300;
    }


    /**
     * 是否失败
     * @param ajaxResult 数据对象
     * @return true:失败 false:成功
     */
    public static boolean isError(AjaxResult ajaxResult){
        return !isSuccess(ajaxResult);
    }
}
