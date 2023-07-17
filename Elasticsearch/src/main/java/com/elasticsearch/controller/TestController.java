package com.elasticsearch.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    ElasticsearchClient ESClient;
    public void test(){
//        ESClient.get()
    }
}
