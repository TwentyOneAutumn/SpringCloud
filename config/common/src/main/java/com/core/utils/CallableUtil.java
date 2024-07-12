package com.core.utils;

import cn.hutool.core.map.MapUtil;
import com.core.doMain.CallableEntity;
import lombok.Data;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Data
public class CallableUtil {

    /**
     * 存储任务名称
     * key:线程ID
     * value:任务名称
     */
    public static final ConcurrentHashMap<Long,String> callableNameMap = new ConcurrentHashMap<>();

    /**
     * 存储开始时间
     * key:线程ID
     * value:开始时间
     */
    public static final ConcurrentHashMap<Long,LocalTime> startTimeMap = new ConcurrentHashMap<>();

    /**
     * 存储线程ID
     */
    public static final ConcurrentLinkedDeque<Long> theadDeque = new ConcurrentLinkedDeque<>();


    /**
     * 初始化，记录任务名称和开始时间
     * @param name 任务名称
     */
    public static void init(String name) {
        Thread thread = Thread.currentThread();
        long threadId = thread.getId();
        if(!theadDeque.contains(threadId)){
            theadDeque.add(threadId);
            callableNameMap.remove(threadId);
            startTimeMap.remove(threadId);
            if(MapUtil.isNotEmpty(callableNameMap)){
                Set<String> callableNameSet = new HashSet<>(callableNameMap.values());
                if(callableNameSet.contains(name)){
                    int i = 0;
                    String onName = name;
                    while (true) {
                        String randomName = onName + "-" + i++;
                        if (!callableNameSet.contains(randomName)) {
                            name = randomName;
                            break;
                        }
                    }
                }
            }
            callableNameMap.put(threadId,name);
            startTimeMap.put(threadId,LocalTime.now());
        }
    }

    /**
     * 构建通用方法
     * @return CallableEntity
     */
    private static <T> CallableEntity<T> build(){
        // 获取线程ID
        Thread thread = Thread.currentThread();
        long threadId = thread.getId();
        // 获取任务名称
        String name = callableNameMap.remove(threadId);
        // 根据任务名称删除并获取开始时间
        LocalTime startTime = startTimeMap.remove(threadId);
        // 获取结束时间
        LocalTime endTime = LocalTime.now();
        // 计算时间差
        long seconds = Duration.between(startTime,endTime).getSeconds();
        // 构建对象
        return new CallableEntity<>(name,seconds);
    }

    /**
     * 构建返回值
     * @param obj 数据对象
     * @return CallableEntity
     */
    public static <T> CallableEntity<T> build(T obj){
        // 初始化对象
        CallableEntity<T> callableEntity = build();
        // 设置信息
        callableEntity.setReturnValue(obj);
        callableEntity.setError(false);
        // 构建对象
        return callableEntity;
    }

    /**
     * 构建返回值
     * @param exception 异常对象
     * @return CallableEntity
     */
    public static <T> CallableEntity<T> error(Exception exception){
        // 初始化对象
        CallableEntity<T> callableEntity = build();
        // 设置信息
        callableEntity.setException(exception);
        callableEntity.setError(true);
        // 构建对象
        return callableEntity;
    }
}
