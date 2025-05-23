package com.udp.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
public class UDPListener {

    @PostConstruct
    public void run() {

        // 使用线程监听UDP
        new Thread(() -> {
            try {
                UDPSocket socket = UDPSocket.create(8206, 102400);
                    while (true) {
                        try {
                            String message = socket.pull();
                            if(StrUtil.isNotEmpty(message)){
                                log.info("收到UDP数据:{}",message);
                            }
                        } catch (IOException e) {
                            log.error("UDP Socket Error", e);
                        }
                    }
            } catch (IOException e) {
                log.error("create UDPSocket error", e);
            }
        }).start();
    }
}
