package com.core.handle;

/**
 * 重写Thread类未捕获异常处理器
 * 用于子线程执行时,抛出的未处理的异常
 * 主线程无法监听子线程异常
 * 一般用于记录日志,调试等
 */
public class ThreadExceptionHandle implements Thread.UncaughtExceptionHandler {

    /**
     * 监听方法
     * 可以调用Thread线程实例的setUncaughtExceptionHandler()方法来设置监听器
     * @param t 线程对象
     * @param e 抛出的异常
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // 写入日志,AOP监听等操作
    }
}
