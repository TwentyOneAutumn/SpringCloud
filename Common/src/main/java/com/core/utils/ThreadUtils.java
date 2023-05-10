package com.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import java.util.List;
import java.util.concurrent.*;

public class ThreadUtils {

    /**
     * 线程局部变量
     */
    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    /**
     * 执行多线程任务,在线程池中所有线程执行完毕,线程池关闭后,结束当前方法
     * @param threadList 已经重写run()方法的Thread类实例集合
     */
    public static void RunThreads(List<Thread> threadList){
        // 获取线程池
        ExecutorService executorService = ThreadUtil.newExecutor();
        // 利用线程池循环执行线程
        threadList.forEach(executorService::execute);
        // 设定在线程池中所有方法执行完毕后关闭线程池
        executorService.shutdownNow();
        while(true){
            // 循环判断线程池是否关闭
            if(executorService.isTerminated()){
                // 如果线程池已关闭,代表线程池中所有线程任务执行完毕,结束当前方法
                return;
            }
        }
    }

    /**
     * 执行多线程任务,在线程池中所有线程执行完毕,线程池关闭后,结束当前方法
     * @param callableList 已经重写call()方法的Callable类实例集合
     * @return List<Future<Object>> 线程返回值
     */
    public static List<Future<Object>> RunCallables(List<Callable<Object>> callableList){
        // 获取线程池
        ExecutorService executorService = ThreadUtil.newExecutor();
        List<Future<Object>> futureList = null;
        try {
            // 执行所有线程任务并获取返回值
            futureList = executorService.invokeAll(callableList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(CollUtil.isNotEmpty(futureList)){// 循环返回值
            futureList.forEach(future -> {
                try {
                    future.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException("线程被中断或被占用");
                } catch (ExecutionException e) {
                    throw new RuntimeException("子线程异常:[" + e.getMessage() + "]");
                }
            });

        }
        return futureList;
    }

    /**
     * 存储的数据到线程变量中
     * @param obj 数据
     * @param <T> 泛型
     */
    public static <T>  void setThreadLocal(T obj){
        threadLocal.set(obj);
    }

    /**
     * 从线程变量中获取数据
     * @param <T> 泛型
     * @return 数据
     */
    public static <T> T getThreadLocal(){
        T obj = null;
        try {
            obj =  (T)threadLocal.get();
        }catch (Exception e){
            throw new RuntimeException("从线程变量中获取数据时类型转换异常");
        }
        return obj;
    }
}
