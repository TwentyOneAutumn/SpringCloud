package com.test;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import com.core.doMain.Row;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotNullTest {

    @Test
    public void test(){
        String message = "{\"code\":200,\"msg\":\"操作成功\",\"row\":\"你好\"}";
        JSONObject jsObject = new JSONObject(message);
        TypeReference<Row<String>> typeReference = new TypeReference<Row<String>>() {
        };
        Row<String> bean = jsObject.toBean(typeReference);
        System.out.println(bean);
    }
}
