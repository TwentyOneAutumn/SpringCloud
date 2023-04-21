package com.demo.Core.Config;

import com.demo.Core.Utils.RedisUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 初始化Redis表缓存
 */
@Component
public class RedisAfterProperties implements InitializingBean {

    // 注入Service
    @Autowired
    RedisUtils redisUtils;

    /**
     * 后置增强方法，用于初始化Redis表缓存
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 刷新表缓存
        redisUtils.refreshAllCache();
    }
}
