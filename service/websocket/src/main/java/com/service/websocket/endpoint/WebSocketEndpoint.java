package com.service.websocket.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket端点
 */
@Slf4j
@ServerEndpoint("/demo")
@Component
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class WebSocketEndpoint {

    /**
     * 连接WebSocket时执行
     * @param session session会话对象
     */
    @OnOpen
    public void onOpen(Session session) {
        // 校验权限
        String queryString = session.getQueryString();
        System.out.println(queryString);
    }

    /**
     * 接收到消息时执行
     * @param message 消息体
     * @param session session会话对象
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(message);
    }

    /**
     * 关闭WebSocket时执行
     * @param session session会话对象
     */
    @OnClose
    public void OnClose(Session session){

    }

    /**
     * WebSocket异常时执行
     * @param session session会话对象
     * @param error 异常对象
     */
    @OnError
    public void OnError(Session session, Throwable error){

    }
}