package com.core.doMain;

import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class CallableUtil {

    /**
     * 存储任务名称和开始时间
     */
    public static final ConcurrentHashMap<String,LocalTime> map = new ConcurrentHashMap<>();

    /**
     * 初始化，记录任务名称和开始时间
     * @param name 任务名称
     */
    public static void init(String name) {
        if(map.containsKey(name)){
            int i = 0;
            String onName = name;
            while (true) {
                String randomName = onName + "-" + i++;
                if (!map.containsKey(randomName)) {
                    name = randomName;
                    break;
                }
            }
        }
        map.put(name, LocalTime.now());
        new ThreadLocal<>().set(name);
    }

    /**
     * 构建返回值
     * @param obj 数据对象
     * @return CallableEntity
     */
    public static <T> CallableEntity<T> build(T obj){
        // 获取任务名称
        String name = new ThreadLocal<String>().get();

        // 根据任务名称删除并获取开始时间
        LocalTime startTime = map.remove(name);
        // 获取结束时间
        LocalTime endTime = LocalTime.now();
        // 计算时间差
        long seconds = Duration.between(startTime,endTime).getSeconds();
        // 构建对象
        return new CallableEntity<>(name,obj,seconds,false);
    }

    /**
     * 构建返回值
     * @param exception 异常对象
     * @return CallableEntity
     */
    public static <T> CallableEntity<T> error(Exception exception){
        // 获取任务名称
        String name = new ThreadLocal<String>().get();
        // 根据任务名称删除并获取开始时间
        LocalTime startTime = map.remove(name);
        // 获取结束时间
        LocalTime endTime = LocalTime.now();
        // 计算时间差
        long seconds = Duration.between(startTime,endTime).getSeconds();
        // 构建对象
        return new CallableEntity<>(name,seconds,true,exception);
    }
}
