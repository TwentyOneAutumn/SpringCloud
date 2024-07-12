package com.gateway.domain;

import lombok.Data;

@Data
public class ResponseError {

    /**
     * 响应状态码
     */
    private Integer code = 500;

    /**
     * 响应信息
     */
    private String msg = "系统异常";

    /**
     * 请求路径
     */
    private String requestPath;
}
