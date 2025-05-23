package com.udp.config;


import cn.hutool.core.util.ArrayUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * 用于监听UDP端口，并获取数据
 */
public class UDPSocket {

    /**
     * 需要监听的UDP端口
     */
    private final int port;

    /**
     * 缓存区
     */
    private final ByteBuffer buffer;

    /**
     * 数据通道
     */
    private final DatagramChannel channel;


    /**
     * 私有化构造
     */
    private UDPSocket(int port, int size) throws IOException {
        this.port = port;
        this.buffer = ByteBuffer.allocate(size);
        this.channel = DatagramChannel.open();
        this.channel.bind(new InetSocketAddress(this.port));
        this.channel.configureBlocking(false);
    }

    /**
     * 静态方法，创建实例
     */
    public static UDPSocket create(int port, int size) throws IOException {
        return new UDPSocket(port,size);
    }

    /**
     * 获取UDP数据
     * <pre>如果你的程序为SpringBoot程序，并想在容器启动后持续监听UDP端口，请参照以下代码:</pre>
     * <pre>
     * {@code
     * import com.core.utils.UDPSocket;
     * import lombok.extern.slf4j.Slf4j;
     * import org.springframework.stereotype.Component;
     * import javax.annotation.PostConstruct;
     * import java.io.IOException;
     *
     * @Slf4j
     * @Component
     * public class UDPListener {
     *
     *     @PostConstruct
     *     public void run(){
     *         // 使用线程监听UDP
     *         new Thread(() -> {
     *             UDPSocket socket = null;
     *             try {
     *                 socket = UDPSocket.create(1234, 102400);
     *             } catch (IOException e) {
     *                 log.error("create UDPSocket error", e);
     *                 return;
     *             }
     *             if(socket != null){
     *                 while(true){
     *                     String message = null;
     *                     try {
     *                         message = socket.pull();
     *                     } catch (IOException e) {
     *                         log.error("UDP Socket Error", e);
     *                     }
     *                     // 处理数据
     *                     // handler(message);
     *                 }
     *             }
     *         }).start();
     *     }
     * }
     * }
     * </pre>
     */
    public String pull() throws IOException {
        // 清空缓存区
        buffer.clear();
        // 读取数据到缓存区并返回源地址
        InetSocketAddress socketAddress = (InetSocketAddress) channel.receive(buffer);
        if(socketAddress != null){
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];

            buffer.get(bytes);
            return ArrayUtil.isNotEmpty(bytes) ? new String(bytes) : null;
        }
        return null;
    }
}
