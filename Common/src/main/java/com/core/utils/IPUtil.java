package com.core.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
public class IPUtil {

    /**
     * 获取IP
     *
     * @param request 请求对象
     * @return IP
     */
    public static String getIpAddr(ServerHttpRequest request) {
        String IP_UNKNOWN = "unknown";
        String IP_LOCAL = "127.0.0.1";
        int IP_LEN = 15;
        HttpHeaders headers = request.getHeaders();
        String ipAddress = headers.getFirst("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = headers.getFirst("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = Optional.ofNullable(request.getRemoteAddress())
                    .map(address -> address.getAddress().getHostAddress())
                    .orElse("");
            if (IP_LOCAL.equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                try {
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取IP失败");
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > IP_LEN) {
            int index = ipAddress.indexOf(",");
            if (index > 0) {
                ipAddress = ipAddress.substring(0, index);
            }
        }
        return ipAddress;
    }
}
