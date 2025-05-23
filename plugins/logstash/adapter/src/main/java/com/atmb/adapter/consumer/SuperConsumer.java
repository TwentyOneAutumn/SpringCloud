package com.atmb.adapter.consumer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.atmb.adapter.es.doc.SuperInfo;
import com.atmb.adapter.es.mapper.DocumentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SuperConsumer<T extends SuperInfo> {

    @Autowired
    private DocumentMapper<T> documentMapper;

    public void push(T pojo, String indexSuffix) {
        try{
            // 判空
            if(BeanUtil.isNotEmpty(pojo)){
                // 写入文档
                documentMapper.save(pojo,pojo.getModule() + indexSuffix);
            }
        } catch (Exception e){
            log.error("新增文档异常",e);
        }
    }
}
