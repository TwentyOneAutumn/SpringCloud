package com.design.pattern.behavioral.singleton;

/**
 * 单例类
 */
public class Singleton {


    /**
     * 私有化构造方法
     */
    private Singleton(){}


    /**
     * 懒汉单例模式
     */
    static class LazySingleton {

        /**
         * 对象实例
         */
        private static volatile Singleton instance;


        /**
         * 获取对象实例
         * @return Singleton
         */
        public static Singleton getInstance() {
            // 双重锁定检查
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


    /**
     * 饿汉单例模式
     */
    static class EagerSingleton{

        /**
         * 对象实例
         */
        private static final Singleton instance = new Singleton();


        /**
         * 获取对象实例
         * @return Singleton
         */
        public static Singleton getInstance(){
            return instance;
        }
    }
}
