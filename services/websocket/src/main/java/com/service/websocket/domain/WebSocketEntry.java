package com.service.websocket.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WebSocketEntry {

    @NotBlank(message = "message参数不能为空")
    private String message;
}
