package com.demo.Gateway.Config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.demo.Common.Utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

/**
 * Gateway网关配置
 */
@Configuration
public class GatewayConfig {

    @Autowired
    RedisTemplate<String, Object> redisClient;

    /**
     * 配置限流规则
     * @return KeyResolver
     */
    @Bean
    KeyResolver keyResolver(){
       return exchange -> {
           // 获取请求的IP地址
           ServerHttpRequest request = exchange.getRequest();
           String ipAddr = IPUtil.getIpAddr(request);
           // 将IP添加到请求头
           exchange.getRequest().mutate().header("IP", ipAddr);
           // 根据客户端IP地址进行限流
           return Mono.just(ipAddr);
        };
    }

    /**
     * 全局过滤器
     * @return GlobalFilter
     */
    @Bean
    GlobalFilter globalFilter(){
        return ( exchange,  chain)->{
            // 获取请求和响应对象
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            // 获取请求路径
            String path = request.getURI().getPath();
            // 判断请求路径是否为登录请求
            if("/user/login/into".equals(path) || "/user/login/auth".equals(path)){
                // 直接放行,登录请求不需要Token
                return chain.filter(exchange);
            }
            // 判断请求是否携带Token
            List<String> tokenList = request.getHeaders().get("token");
            // Token为空,响应错误信息
            if(CollectionUtils.isEmpty(tokenList)){
                return AuthFailed(response,"请先进行认证");
            }
            // 获取请求携带Token
            String requestToken = tokenList.get(0);
            // 获取IP
            String ip = IPUtil.getIpAddr(request);
            // 根据IP获取Token
            String token = (String)redisClient.opsForValue().get(ip);
            if(!token.equals(requestToken)){
                return AuthFailed(response,"Token无效");
            }
            return chain.filter(exchange);
        };
    }

    /**
     * 配置跨域问题
     * @return CorsWebFilter
     */
    @Bean
    CorsWebFilter corsWebFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    /**
     * 配置认证失败响应结果
     * @param response 响应对象
     * @return Mono<Void>
     */
    private Mono<Void> AuthFailed(ServerHttpResponse response,String msg) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", "500");
        map.put("success", "false");
        map.put("msg", msg);
        JSON json = new JSONObject(map);
        byte[] bits = json.toJSONString(4).getBytes(StandardCharsets.UTF_8);
//        byte[] bits = properties.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
