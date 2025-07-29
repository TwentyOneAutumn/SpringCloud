package com.wecom.domain.entry;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据封装
 *
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
    private T rows;

    protected Row(int code, String msg, T row) {
        this.code = code;
        this.msg = msg;
        this.rows = row;
    }

    protected Row(T row) {
        this.code = HttpStatus.HTTP_OK;
        this.msg = "操作成功";
        this.rows = row;
    }

    /**
     * 获取数据
     *
     * @param auth 是否验证数据为空
     * @param msg  错误信息
     * @return 数据
     */
    public T data(boolean auth, String msg) {
        if (code > 199 && code < 300) {
            if (!(auth && BeanUtil.isEmpty(rows))) {
                return rows;
            }
        }
        throw new RuntimeException(msg);
    }

    /**
     * 获取数据
     *
     * @param auth      是否验证数据为空
     * @param exception 异常对象
     * @return 数据
     */
    public <E extends Exception> T data(boolean auth, E exception) throws E {
        if (code > 199 && code < 300) {
            if (!(auth && BeanUtil.isEmpty(rows))) {
                return rows;
            }
        }
        throw exception;
    }

    /**
     * 是否成功
     */
    public T isSuccess() {
        if (code > 199 && code < 300) {
            return rows;
        } else {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 是否成功
     */
    public <E> E isSuccess(Class<E> clazz) {
        if (code > 199 && code < 300) {
            return BeanUtil.toBean(rows, clazz);
        } else {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 是否成功
     */
    public <E,X extends Throwable> E isSuccess(Class<E> clazz, X exception) throws X {
        if (code > 199 && code < 300) {
            return BeanUtil.toBean(rows, clazz);
        } else {
            throw exception;
        }
    }
}