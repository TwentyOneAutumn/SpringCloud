package com.test.sync.object;

/**
 * 测试类
 */
public class Main {

    /**
     * 模仿多线程高并发环境
     */
    public static void main(String[] args) {
        // 创建任务对象
        SyncObject sync = new SyncObject();

        // 构建Runnable,针对同一个对象进行操作
        Runnable runnable = () -> {
            // 更新版本号
            int version = sync.updateVersion();
            // 打印版本号
            System.out.println(version);
        };

        // 基于Runnable构建线程对象
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        // 启动线程
//        thread1.start();
        thread2.start();
        thread3.start();
    }
}
