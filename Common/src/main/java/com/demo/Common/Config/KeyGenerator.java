package com.demo.Common.Config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator implements IKeyGenerator {
    @Override
    public String executeSql(String incrementerName) {
        return "select uuid()";
    }
}
