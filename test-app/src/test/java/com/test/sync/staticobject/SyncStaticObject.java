package com.test.sync.staticobject;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 同步代码块类
 */
@Data
public class SyncStaticObject {

    /**
     * 版本号
     */
    private static int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public int updateVersion(){
        // 同步代码块,给当前Class对象上锁,只有拿到了当前类Class对象的锁才能执行代码块中的内容
        synchronized (SyncStaticObject.class){
            // 设置版本号
            version++;

            // 模拟执行其他业务逻辑...
            Thread.sleep(2000);
        }
        // 返回更新后的版本号
        return version;
    }
}
