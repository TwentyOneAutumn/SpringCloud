//package com.es.listener;
//
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONObject;
//import com.core.utils.UDPSocket;
//import com.es.doc.RequestInfo;
//import com.es.service.IRequestInfoService;
//import javax.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class RequestInfoListener {
//
//
//    @Autowired
//    private IRequestInfoService requestInfoService;
//
//    private static final UDPSocket socket;
//
//    static {
//        try {
//            socket = UDPSocket.create(7001, 102400);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    @PostConstruct
//    public void run(){
//        // 使用线程监听UDP
//        Thread requestListenerThread = new Thread(() -> {
//            while (true) {
//                String message = null;
//                try {
//                    // 获取数据
//                    message = socket.pull();
//                } catch (Exception e) {
//                    log.error("UDP Socket Error", e);
//                }
//
//                // 写入数据
//                if (StrUtil.isNotBlank(message)) {
//                    RequestInfo data = new JSONObject(message).toBean(RequestInfo.class);
//                    requestInfoService.documentSave(data);
//                }
//            }
//        });
//
//        // 启动线程
//        requestListenerThread.start();
//    }
//}
