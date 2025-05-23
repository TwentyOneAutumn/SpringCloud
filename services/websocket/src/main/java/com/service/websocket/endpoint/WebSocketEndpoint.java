package com.service.websocket.endpoint;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket端点
 */
@Slf4j
@ServerEndpoint("/inform")
@Component
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class WebSocketEndpoint {

    /**
     * 用于存储所有连接的会话
     */
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到消息: {}", message);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket错误:{}", error.getMessage());
    }

    public static void sendMessageToAll(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    // 推送消息
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("发送消息失败:{}",e.getMessage());
                }
            }
        }
    }
}