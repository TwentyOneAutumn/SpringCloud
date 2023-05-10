package com.Core.Utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.Core.Lock.RedisCacheLock;
import com.baomidou.mybatisplus.annotation.TableId;
import com.Core.Enums.RedisTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class RedisUtils {

    @Autowired
    RedisTemplate<String, Object> redisClient;

    @Autowired
    RedisCacheLock redisCacheLock;

    /**
     * 清空Redis缓存
     */
    public void clear(){
        // 清除Redis中所有缓存
        Set<String> keySet = redisClient.keys("*");
        if(CollUtil.isNotEmpty(keySet)){
            redisClient.delete(keySet);
        }
    }

    /**
     * 刷新表缓存
     * @param tableName 表名称
     */
    public void refreshTableCache(String tableName){
        // 判断锁是否被持有
        if(redisCacheLock.isLocked(tableName)){
            // 如果锁被持有，就加入锁等待队列

        }else {
            try {
                // 如果锁未被持有，获取锁，并上锁
            }catch (Exception e){
                throw new RuntimeException("刷新缓存异常");
            }finally {
                // 解锁当前线程所持有的锁
            }
        }

        // 判断缓存是否存在
        if(Boolean.TRUE.equals(redisClient.hasKey(tableName))){
            // 清空表缓存
            redisClient.delete(tableName);
        }
        // TODO 获取最新表数据
        List<Object> list = null;
        // 获取ID名称
        String idName = getIdName(StreamUtils.findAny(list));
        // 封装表数据为Map
        Map<String,Object> map = new HashMap<>();
        list.forEach(pojo -> map.put(getIdValue(pojo,idName),pojo));
        try {
            // 添加Hash
            redisClient.opsForHash().putAll(tableName,map);
        }catch (Exception e){
            throw new RuntimeException("刷新缓存异常");
        }
    }

    /**
     * 刷新所有缓存
     */
    public void refreshAllCache(){
        clear();
        // 初始化缓存
        for (String tableName : RedisTableName.TABLE_NAME_ARR) {
            refreshTableCache(tableName);
        }
    }

    /**
     * 获取所有表数据
     * @param tableName 表名
     * @param <T> 泛型
     * @return 表数据
     */
    public <T> List<T> getAll(String tableName){
        List<T> list = new ArrayList<>();
        // 获取缓存
        try {
            // 判断Key是否存在
            if(Boolean.TRUE.equals(redisClient.hasKey(tableName))){
                list = (List<T>) redisClient.opsForHash().values(tableName);
                HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisClient.opsForHash();
            }else {
                throw new RuntimeException();
            }
        }catch (Exception e){
            // Redis缓存异常，从数据库获取数据
            throw new RuntimeException("读取缓存异常");
        }
        return list;
    }

    /**
     * 根据多个ID获取数据
     * @param tableName 表名
     * @param <T> 泛型
     * @return 表数据
     */
    public <T> List<T> getAll(String tableName, List<String> idList){
        List<T> list = new ArrayList<>();
        // 获取缓存
        try {
            // 判断Key是否存在
            if(redisClient.hasKey(tableName)){
                idList.forEach(id -> list.add((T)redisClient.opsForHash().get(tableName, id)));
            }else {
                throw new RuntimeException();
            }
        }catch (Exception e){
            // Redis缓存异常，从数据库获取数据
            throw new RuntimeException("读取缓存异常");
        }
        return list;
    }

    /**
     * 根据ID获取数据
     * @param tableName 表名
     * @param <T> 泛型
     * @return 表数据
     */
    public <T> T getById(String tableName,String id){
        T row = null;
        // 获取缓存
        try {
            // 判断Key是否存在
            if(redisClient.hasKey(tableName)){
                row = (T) redisClient.opsForHash().get(tableName, id);
            }else {
                throw new RuntimeException();
            }
        }catch (Exception e){
            // Redis缓存异常，从数据库获取数据
            throw new RuntimeException("读取缓存异常");
        }
        return row;
    }

    /**
     * 获取对象的主键ID名称
     * @param object 对象
     * @return 对象的主键ID名称
     */
    public String getIdName(Object object){
        // 获取Class
        Class<?> aClass = object.getClass();
        // 获取字段
        Field[] fields = aClass.getFields();
        AtomicReference<String> idFieldName = new AtomicReference<>();
        // 循环字段数组，获取主键ID
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            // 判断是否有主键注解
            if(BeanUtil.isNotEmpty(field.getAnnotation(TableId.class))){
                // 获取ID字段
                idFieldName.set(field.getName());
            }
        });
        String idName = idFieldName.get();
        if(StrUtil.isNotEmpty(idName)){
            return idName;
        }else {
            throw new RuntimeException("获取主键ID名称异常");
        }
    }

    /**
     * 获取对象的主键ID值
     * @param object 对象
     * @return 对象的主键ID值
     */
    public String getIdValue(Object object, String idName){
        // 获取Class
        Class<?> aClass = object.getClass();
        // 获取主键ID字段
        Field idField = null;
        try {
            idField = aClass.getField(idName);
        } catch (Exception e) {
            throw new RuntimeException("获取主键ID异常");
        }
        AtomicReference<String> idFieldName = new AtomicReference<>();
        // 循环字段数组，获取主键ID值
        idField.setAccessible(true);
        String idValue = null;
        try {
            idValue = (String)idField.get(object);
        } catch (Exception e) {
            throw new RuntimeException("获取主键ID值异常");
        }
        if(StrUtil.isNotEmpty(idValue)){
            return idValue;
        }else {
            throw new RuntimeException("获取主键ID值异常");
        }
    }
}
