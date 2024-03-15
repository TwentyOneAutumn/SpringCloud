package com.test.sync.method;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 同步实例方法类
 */
@Data
public class SyncMethod {

    /**
     * 版本号
     */
    private int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public synchronized int updateVersion(){
        // 设置版本号
        this.version++;

        // 模拟执行其他业务逻辑...
        Thread.sleep(2000);

        // 返回更新后的版本号
        return this.version;
    }
}
