package com.demo.Core.Lock;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.demo.Core.DoMain.MapEntry;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义Redis缓存锁
 */
@Component
public class RedisCacheLock{

    /**
     * 线程等待队列
     * 元素:线程ID
     */
    private static final LinkedList<String> LockWaitQueue = new LinkedList<>();

    /**
     * 锁
     */
    private static final Sync sync = Sync.build();

    /**
     * 自定义同步器，继承AbstractQueuedSynchronizer
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 私有化构造方法
         */
        private Sync(){}

        /**
         * 是否持有锁
         */
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 获取锁
         */
        protected boolean tryAcquire(int acquires) {
            assert acquires == 1;
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放锁
         */
        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 提供静态方法，获取锁对象
         */
        public static Sync build(){
            return new Sync();
        }
    }

    /**
     * 上锁
     */
    public void lock(Sync sync) {
        sync.acquire(1);
    }

    /**
     * 解锁
     */
    public void unlock(String serviceName) {
        sync.release(1);
    }

    /**
     * 当前线程当前服务上锁
     * @param serviceName 服务名
     */
    public synchronized Sync lock(String serviceName){
        // 获取锁
        Sync sync = Sync.build();
        // 对线程等待队列上锁
        synchronized (LockWaitQueue){
            if(LockWaitQueue.containsKey(serviceName)){
                throw new RuntimeException("锁异常");
            }
            // 往线程等待队列中写入服务名，线程ID，锁ID
            LockWaitQueue.put(serviceName, ListUtil.toLinkedList(new MapEntry<>(Thread.currentThread().getId(), sync)));
            return sync;
        }
    }

    /**
     * 解锁
     * @param serviceName 服务名
     */
    public synchronized void unlock(String serviceName,Sync sync){
        // 对线程等待队列上锁
        synchronized (LockWaitQueue){
            boolean isError = false;
            try {
                if(LockWaitQueue.containsKey(serviceName)){
                    // 获取锁
                    LinkedList<MapEntry<Long, Sync>> queue = LockWaitQueue.get(serviceName);
                    // 获取队列中头节点
                    MapEntry<Long, Sync> first = queue.getFirst();
                    // 获取线程ID
                    Long key = first.getKey();
                    // 判断是否为当前线程
                    if(key.equals(Thread.currentThread().getId())){
                        // 如果为当前线程则取出锁
                        Sync lock = first.getValue();
                        if(BeanUtil.isNotEmpty(sync) && sync.equals(lock) && sync.isHeldExclusively()){
                            // 解锁
                            boolean isLock = sync.release(1);
                            // 判断解锁是否成功
                            if(isLock){
                                // 解锁成功则从队列中删除该线程
                                queue.removeFirst();
                            }else {
                                throw new RuntimeException("锁异常");
                            }
                        }else {
                            throw new RuntimeException("锁异常");
                        }
                    }else {
                        throw new RuntimeException("锁异常");
                    }
                }else {
                    throw new RuntimeException("锁异常");
                }
            }catch (Exception e){
                isError = true;
            }finally {
                // 如果出现异常，循环并释放所有锁，清空锁队列
                if(isError){
                    LockWaitQueue.get(serviceName).forEach(entry -> entry.getValue().release(1));
                    LockWaitQueue.remove(serviceName);
                }
            }
        }
    }

    /**
     * 根据服务名 判断缓存锁是否被持有
     * @param serviceName 服务名
     */
    public synchronized boolean isLocked() {
        return sync.;
    }

    /**
     * 使当前线程加入锁等待队列
     * @param serviceName 服务名
     */
    public synchronized void joinLockWaitQueue(String serviceName){
        // 获取锁
        Sync sync = Sync.build();
        // 对线程等待队列上锁
        synchronized (LockWaitQueue){
            if(LockWaitQueue.containsKey(serviceName)){
                LinkedList<MapEntry<Long, Sync>> queue = LockWaitQueue.get(serviceName);
                queue.addLast(new MapEntry<>(Thread.currentThread().getId(), sync));
            }else {
                throw new RuntimeException("锁异常");
            }
        }
    }

    /**
     * 1.当前线程上锁
     * 2.当前线程加入锁等待队列
     * 3.当前线程解锁
     * 4.判断当前线程是否是队列的最后一个，如果不是，释放锁，如果是，则执行响相应操作
     */
}