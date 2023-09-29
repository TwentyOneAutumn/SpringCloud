package com.core.doMain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamProxy<T> {

    /**
     * 需要操作的集合的Stream
     */
    private final Stream<T> stream;


    /**
     * 构造方法
     * @param collection 集合，不能为空
     */
    public StreamProxy(Collection<T> collection) {
        if (CollUtil.isNotEmpty(collection)){
            this.stream = collection.stream();
        }else {
            throw new IllegalStateException("Collection Not Empty");
        }
    }

    public List<T> toList(){
        return stream.collect(Collectors.toList());
    }

    public List<T> toList(boolean isDistinct){
        return isDistinct ? stream.distinct().collect(Collectors.toList()) : toList();
    }

    public <E> List<E> toList(Class<E> type) {
        return stream.map(item -> {
            try {
                return BeanUtil.toBean(item, type);
            } catch (Exception e) {
                throw new IllegalStateException("类型转换错误");
            }
        }).collect(Collectors.toList());
    }

    public <E> List<E> toList(Class<E> type, Boolean isDistinct) {
        if (BeanUtil.isEmpty(isDistinct)) {
            return toList(type);
        } else {
            return isDistinct ?
                    stream.distinct().map(item -> {
                        try {
                            return BeanUtil.toBean(item, type);
                        } catch (Exception e) {
                            throw new IllegalStateException("类型转换错误");
                        }
                    }).collect(Collectors.toList()) :
                    stream.map(item -> {
                        try {
                            return BeanUtil.toBean(item, type);
                        } catch (Exception e) {
                            throw new IllegalStateException("类型转换错误");
                        }
                    }).distinct().collect(Collectors.toList());
        }

    }

    public <E> List<E> toList(Function<T,E> mapper) {
        return stream.map(mapper).collect(Collectors.toList());
    }

    public <E> List<E> toList(Function<T,E> mapper, Boolean isDistinct) {
        if(BeanUtil.isEmpty(isDistinct)){
            return toList(mapper);
        }else {
            return isDistinct ? stream.distinct().map(mapper).collect(Collectors.toList()) : stream.map(mapper).distinct().collect(Collectors.toList());
        }
    }

    public Set<T> toSet(){
        return stream.collect(Collectors.toSet());
    }

    public Set<T> toSet(boolean isDistinct){
        return isDistinct ? stream.distinct().collect(Collectors.toSet()) : toSet();
    }

    public <E> Set<E> toSet(Class<E> type){
        return stream.map(item -> {
            try {
                return BeanUtil.toBean(item, type);
            } catch (Exception e) {
                throw new IllegalStateException("类型转换错误");
            }
        }).collect(Collectors.toSet());
    }

    public <E> Set<E> toSet(Class<E> type, Boolean isDistinct){
        if(BeanUtil.isEmpty(isDistinct)){
            return toSet(type);
        }else {
            return isDistinct ?
                    stream.distinct().map(item -> {
                        try {
                            return BeanUtil.toBean(item, type);
                        } catch (Exception e) {
                            throw new IllegalStateException("类型转换错误");
                        }
                    }).collect(Collectors.toSet()) :
                    stream.map(item -> {
                        try {
                            return BeanUtil.toBean(item, type);
                        } catch (Exception e) {
                            throw new IllegalStateException("类型转换错误");
                        }
                    }).distinct().collect(Collectors.toSet());
        }
    }

    public <E> Set<E> toSet(Function<T,E> mapper){
        return stream.map(mapper).collect(Collectors.toSet());
    }

    public <E> Set<E> toSet(Function<T,E> mapper, Boolean isDistinct){
        if(BeanUtil.isEmpty(isDistinct)){
            return toSet(mapper);
        }else {
            return isDistinct ? stream.distinct().map(mapper).collect(Collectors.toSet()) : stream.map(mapper).distinct().collect(Collectors.toSet());
        }
    }

    public T[] toArray(){
        return stream.toArray(size -> (T[]) new Object[size]);
    }

    public T[] toArray(boolean isDistinct){
        return isDistinct ? stream.distinct().toArray(size -> (T[]) new Object[size]) : toArray();
    }

    public <E> E[] toArray(Class<E> type){
        return stream.map(item -> {
            try {
                return BeanUtil.copyProperties(item, type);
            } catch (Exception e) {
                throw new IllegalStateException("集合元素类型转换异常");
            }
        }).toArray(size -> (E[]) new Object[size]);
    }

    public <E> E[] toArray(Class<E> type, Boolean isDistinct){
        if(BeanUtil.isEmpty(isDistinct)){
            return toArray(type);
        }else {
            return isDistinct ?
                    stream.distinct().map(item -> {
                        try {
                            return BeanUtil.toBean(item, type);
                        } catch (Exception e) {
                            throw new IllegalStateException("类型转换错误");
                        }
                    }).toArray(size -> (E[]) new Object[size]) :
                    stream.map(item -> {
                        try {
                            return BeanUtil.toBean(item, type);
                        } catch (Exception e) {
                            throw new IllegalStateException("类型转换错误");
                        }
                    }).distinct().toArray(size -> (E[]) new Object[size]);
        }
    }

    public <E> E[] toArray(Function<T,E> mapper){
        return stream.map(mapper).toArray(size -> (E[]) new Object[size]);
    }

    public <E> E[] toArray(Function<T,E> mapper, Boolean isDistinct){
        if(BeanUtil.isEmpty(isDistinct)){
            return toArray(mapper);
        }else {
            return isDistinct ? stream.distinct().map(mapper).toArray(size -> (E[]) new Object[size]) : stream.map(mapper).distinct().toArray(size -> (E[]) new Object[size]);
        }
    }
    
    /**
     * 过滤集合
     * @param predicate 过滤器
     * @return 过滤后的集合
     */
    public List<T> filterToList(Predicate<T> predicate){
        return stream.filter(predicate).collect(Collectors.toList());
    }

    /**
     * 过滤集合并去重
     * @param predicate 匹配器
     * @return 过滤后的集合
     */
    public  List<T> filterToList(Predicate<T> predicate, Boolean isDistinct){
        if(BeanUtil.isEmpty(isDistinct)){
            return filterToList(predicate);
        } else {
            return isDistinct ? stream.distinct().filter(predicate).collect(Collectors.toList()) : stream.filter(predicate).distinct().collect(Collectors.toList());
        }
    }

    /**
     * 过滤集合
     * @param predicate 过滤器
     * @return 过滤后的集合
     */
    public Set<T> filterToSet(Predicate<T> predicate){
        return stream.filter(predicate).collect(Collectors.toSet());
    }

    /**
     * 排序集合
     * @param comparator 比较器
     * @return 排序后的集合
     */
    public List<T> sortedToList(Comparator<T> comparator){
        return stream.sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 排序集合
     * @param comparator 比较器
     * @return 排序后的集合
     */
    public List<T> sortedDistToList(Comparator<T> comparator){
        return stream.sorted(comparator).distinct().collect(Collectors.toList());
    }

    /**
     * 排序集合
     * @param comparator 比较器
     * @return 排序后的集合
     */
    public Set<T> sortedToSet(Comparator<T> comparator){
        return stream.sorted(comparator).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * 集合过滤后转换泛型
     * @param clazz 要转换的类型
     * @param <E> 集合元素泛型
     * @param <E> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public <E> List<E> filterMapToList(Predicate<T> predicate, Class<E> clazz){
        return stream.filter(predicate).map(v -> {
            try {
                return BeanUtil.copyProperties(v, clazz);
            } catch (Exception ex) {
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).collect(Collectors.toList());
    }

    /**
     * 集合过滤后转换泛型并去重
     * @param clazz 要转换的类型
     * @param <E> 转换后集合元素泛型
     * @return 过滤转换去重后的集合
     */
    public  <E> List<E> filterMapDistToList( Predicate<T> predicate, Class<E> clazz){
        return stream.filter(predicate).map(v -> {
            try {
                return BeanUtil.copyProperties(v, clazz);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).distinct().collect(Collectors.toList());
    }

    /**
     * 集合过滤后转换泛型
     * @param clazz 要转换的类型
     * @param <E> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public  <E> Set<E> filterMapToSet( Predicate<T> predicate, Class<E> clazz){
        return stream.filter(predicate).map(v -> {
            try {
                return BeanUtil.copyProperties(v, clazz);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).collect(Collectors.toSet());
    }

    /**
     * 集合过滤后转换泛型
     * @param mapper 映射器
     * @param <E> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public  <E> List<E> filterMapToList( Predicate<T> predicate, Function<T, E> mapper){
        return stream.filter(predicate).map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合过滤后转换泛型
     * @param mapper 映射器
     * @param <E> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public  <E> List<E> filterMapDistToList( Predicate<T> predicate, Function<T, E> mapper){
        return stream.filter(predicate).map(mapper).distinct().collect(Collectors.toList());
    }


    /**
     * 集合过滤后转换泛型
     * @param mapper 映射器
     * @param <E> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public  <E> Set<E> filterMapToSet( Predicate<T> predicate, Function<T, E> mapper){
        return stream.filter(predicate).map(mapper).collect(Collectors.toSet());
    }

    /**
     * 判断集合中所有元素是否全部满足条件
     * @param predicate 匹配器匹配器
     * @param isAll true:全部匹配 false:全部不匹配
     * @return true:全部满足条件 false:有元素不满足条件
     */
    public boolean match( Predicate<T> predicate,boolean isAll){
        return isAll ? stream.allMatch(predicate) : stream.noneMatch(predicate);
    }

    /**
     * 判断集合中是否有元素满足条件
     * @param predicate 匹配器匹配器
     * @return true:至少有一个满足条件 false:所有元素都不满足条件
     */
    public boolean match( Predicate<T> predicate){
        return stream.anyMatch(predicate);
    }

    /**
     * 获取当前集合中最大值元素
     * @param comparator 比较器
     * @return 集合中元素比较后的最大值对象
     * @throws RuntimeException 该方法在取不到最大值或最大值为Null的情况下抛出RuntimeException:获取当前集合中比较后元素最大值异常
     */
    public T max(Comparator<T> comparator) throws RuntimeException{
        return stream.max(comparator).orElseThrow(() -> new RuntimeException("获取当前集合中比较后元素最大值异常"));
    }

    /**
     * 获取当前集合中最小值元素
     * @param comparator 比较器
     * @return 集合中元素比较后的最小值对象
     * @throws RuntimeException 该方法在取不到最小值或最小值为Null的情况下抛出RuntimeException:获取当前集合中比较后元素最小值异常
     */
    public T min(Comparator<T> comparator) throws RuntimeException{
        return stream.min(comparator).orElseThrow(() -> new RuntimeException("获取当前集合中比较后元素最小值异常"));
    }

    /**
     * 随机获取当前集合中一个元素
     * @return 当前流中随机一个元素
     * @throws RuntimeException 该方法在取不到元素或取到元素为Null的情况下抛出RuntimeException:随机获取当前集合中一个元素异常
     */
    public T findAny()throws RuntimeException{
        return stream.findAny().orElseThrow(() -> new RuntimeException("随机获取当前集合中一个元素异常"));
    }

    /**
     * 随机获取当前流中一个元素
     * @param stream Stream流对象
     * @return 当前流中随机一个元素
     * @throws RuntimeException 该方法在取不到元素或取到元素为Null的情况下抛出RuntimeException:随机获取当前集合中一个元素异常
     */
    public T findAny(Stream<T> stream)throws RuntimeException{
        return stream.findAny().orElseThrow(() -> new RuntimeException("随机获取当前集合中一个元素异常"));
    }

    /**
     * 获取当前集合过滤后元素数量
     * @param predicate 匹配器
     * @return 过滤后元素数量
     */
    public long filterCount( Predicate<T> predicate){
        return stream.filter(predicate).count();
    }

    /**
     * 获取当前集合过滤去重后元素数量
     * @param predicate 匹配器
     * @return 过滤去重后元素数量
     */
    public long filterDistCount(Predicate<T> predicate){
        return stream.filter(predicate).distinct().count();
    }

    /**
     * 根据集合元素某个字段进行分组
     * @param classifier 类映射器
     * @return 分组后集合 Map<K,V> K:作为分组条件的字段 V:根据条件分组后的对象集合
     */
    public <E> Map<E,List<T>> groupBy(Function<T,E> classifier){
        return stream.collect(Collectors.groupingBy(classifier));
    }

    /**
     * 去重后根据集合元素某个字段进行分组
     * @param classifier 类映射器
     * @param <E> 集合元素泛型
     * @return 去重分组后集合 Map<K,V> K:作为分组条件的字段 V:根据条件分组后的对象集合
     */
    public <E> Map<E,List<T>> groupByDist(Function<T,E> classifier){
        return stream.distinct().collect(Collectors.groupingBy(classifier));
    }

    /**
     * 合并多个集合,返回新集合
     * @param collections 集合对象
     * @return 合并后的新集合
     */
    public List<T> mergeToList(Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * 合并多个集合,并去重,返回新集合
     * @param collections 集合对象
     * @return 合并去重后的新集合
     */
    public List<T> mergeAsDistToList(Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,返回新集合
     * @param predicate 匹配器
     * @param collections 集合对象
     * @return 合并过滤后的新集合
     */
    public List<T> mergeAsFilterToList(Predicate<T> predicate, Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,去重,返回新集合
     * @param predicate 匹配器
     * @param collections 集合对象
     * @return 合并过滤后去重的新集合
     */
    public List<T> mergeDistToList(Predicate<T> predicate, Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).distinct().collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,进行泛型转换,然后返回新集合
     * 基于映射器转换泛型
     * @param predicate 匹配器
     * @param mapper 映射器
     * @param collections 集合对象数组
     * @param <E> 转换后集合元素泛型
     * @return 合并过滤泛型转换后的新集合
     */
    public <E> List<E> mergeToList(Predicate<T> predicate,Function<T,E> mapper,Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).map(mapper).collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,进行泛型转换,去重,然后返回新集合
     * 基于映射器转换泛型
     * @param predicate 匹配器
     * @param mapper 映射器
     * @param collections 集合对象数组
     * @param <E> 转换后集合元素泛型
     * @return 合并过滤泛型转换去重后的新集合
     */
    public  <E> List<E> mergeDistToList(Predicate<T> predicate,Function<T,E> mapper,Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).map(mapper).distinct().collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,进行泛型转换,然后返回新集合
     * 基于映射器转换泛型
     * @param predicate 匹配器
     * @param mapper 映射器
     * @param collections 集合对象数组
     * @param <E> 转换后集合元素泛型
     * @return 合并过滤泛型转换后的新集合
     */
    public  <E> Set<E> mergeToSet(Predicate<T> predicate,Function<T,E> mapper,Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).map(mapper).collect(Collectors.toSet());
    }

    /**
     * 合并多个集合,返回新集合
     * @param collections 集合对象
     * @return 合并后的新集合
     */
    public Set<T> mergeToSet(Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    /**
     * 合并多个集合,过滤后,返回新集合
     * @param predicate 匹配器
     * @param collections 集合对象
     * @return 合并过滤后的新集合
     */
    public Set<T> mergeToSet(Predicate<T> predicate, Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).collect(Collectors.toSet());
    }

    /**
     * 合并多个集合,并去重,返回新集合
     * @param collections 集合对象
     * @return 合并去重后的新集合
     */
    public Set<T> mergeAsDistToSet(Collection<T>... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
