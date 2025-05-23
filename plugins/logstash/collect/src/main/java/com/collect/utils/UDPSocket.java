package com.collect.utils;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPSocket {

    private DatagramPacket packet;

    private DatagramSocket socket;

    public UDPSocket(String host, int port) throws UnknownHostException, SocketException {
        this.packet = new DatagramPacket(new byte[]{}, 0, InetAddress.getByName(host), port);
        this.socket = new DatagramSocket();
    }

    public static UDPSocket create(String host, int port) throws UnknownHostException, SocketException {
        return new UDPSocket(host, port);
    }

    public static UDPSocket create(String address) throws UnknownHostException, SocketException {
        String[] split = address.split(":");
        return new UDPSocket(split[0], Integer.parseInt(split[1]));
    }

    /**
     * 发送UDP数据到指定地址
     */
    public  void push(byte[] bytes) throws IOException {
        packet.setData(bytes,0,bytes.length);
        socket.send(packet);
    }

    /**
     * 发送UDP数据到指定地址
     */
    public  void push(String data) throws IOException {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        packet.setData(bytes,0,bytes.length);
        socket.send(packet);
    }
}
