package com.service.websocket.controller;

import com.service.websocket.domain.Build;
import com.service.websocket.domain.Result;
import com.service.websocket.domain.WebSocketEntry;
import com.service.websocket.endpoint.WebSocketEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/websocket")
public class WebSocketController {

    @GetMapping("/send")
    public Result send(@Valid WebSocketEntry entry) {
        WebSocketEndpoint.sendMessageToAll(entry.getMessage());
        return Build.result(true);
    }
}
