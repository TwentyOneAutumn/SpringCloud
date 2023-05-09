package com.demo.Core.Config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import org.springframework.stereotype.Component;

/**
 * MybatisPlus表主键生成器
 */
@Component
public class KeyGenerator implements IKeyGenerator {

    /**
     * 表主键生成策略
     * @param incrementerName – 序列名称(对应类上注解 KeySequence.value() 的值)
     * @return 主键
     */
    @Override
    public String executeSql(String incrementerName) {
        return "select uuid()";
    }
}
