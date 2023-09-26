package com.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Test
    @Transactional
    public void test() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8088/basic/user/list?pageSize=10&pageNum=1";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        int statusCodeValue = forEntity.getStatusCodeValue();
        String body = forEntity.getBody();
        System.out.println(statusCodeValue);
        System.out.println(body);
    }
}
