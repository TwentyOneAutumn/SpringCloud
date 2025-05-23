//package com.atmb.adapter.config;
//
//import cn.hutool.json.JSONObject;
//import org.apache.kafka.common.serialization.Deserializer;
//import org.apache.poi.ss.formula.functions.T;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class JsonDeserializer implements Deserializer<T> {
//    @Override
//    public T deserialize(String topic, byte[] data) {
//        String json = new String(data, StandardCharsets.UTF_8);
//
//        T bean = new JSONObject(json).toBean(T.class);
//        return bean;
//    }
//}
