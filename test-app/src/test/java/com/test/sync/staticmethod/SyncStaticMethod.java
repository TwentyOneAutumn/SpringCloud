package com.test.sync.staticmethod;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 同步静态方法类
 */
@Data
public class SyncStaticMethod {

    /**
     * 版本号
     */
    private static int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public static synchronized int updateVersion(){
        // 设置版本号
        version++;

        // 模拟执行其他业务逻辑...
        Thread.sleep(2000);

        // 返回更新后的版本号
        return version;
    }
}
