package com.design.pattern.behavioral.singleton;

/**
 * 单例类
 */
public class Singleton {

    /**
     *
     */
    private static volatile Singleton instance;

    /**
     * 私有构造方法
     */
    private Singleton() {
    }

    /**
     * 获取对象实例
     * @return Singleton
     */
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
