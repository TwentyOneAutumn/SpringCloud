package com.test.sync.object;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 同步代码块类
 */
@Data
public class SyncObject {

    /**
     * 版本号
     */
    private int version = 0;

    /**
     * 更新版本号
     */
    @SneakyThrows
    public int updateVersion(){
        // 同步代码块,给当前类对象上锁,只有拿到了当前类的锁才能执行代码块中的内容
        synchronized (this){
            // 设置版本号
            version++;

            // 模拟执行其他业务逻辑...
            Thread.sleep(2000);
        }
        // 返回更新后的版本号
        return version;
    }
}
