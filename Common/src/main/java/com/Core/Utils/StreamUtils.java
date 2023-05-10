package com.Core.Utils;

import cn.hutool.core.bean.BeanUtil;
import com.demo.Core.Interface.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基于Stream流封装的工具类
 */
public class StreamUtils<T>{

    /**
     * 过滤集合
     * @param collection 集合对象
     * @param predicate 过滤器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 过滤后的集合
     */
    public static <T extends Collection<E>,E> List<E> filterToList(T collection, Predicate<E> predicate){
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 过滤集合并去重
     * @param collection 集合对象
     * @param predicate 匹配器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 过滤后的集合
     */
    public static <T extends Collection<E>,E> List<E> filterDistToList(T collection, Predicate<E> predicate){
        return collection.stream().filter(predicate).distinct().collect(Collectors.toList());
    }

    /**
     * 过滤集合
     * @param collection 集合对象
     * @param predicate 过滤器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 过滤后的集合
     */
    public static <T extends Collection<E>,E> Set<E> filterToSet(T collection, Predicate<E> predicate){
        return collection.stream().filter(predicate).collect(Collectors.toSet());
    }

    /**
     * 排序集合
     * @param collection 集合对象
     * @param comparator 比较器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 排序后的集合
     */
    public static <T extends Collection<E>,E> List<E> sortedToList(T collection,Comparator<E> comparator){
        return collection.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 排序集合
     * @param collection 集合对象
     * @param comparator 比较器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 排序后的集合
     */
    public static <T extends Collection<E>,E> List<E> sortedDistToList(T collection,Comparator<E> comparator){
        return collection.stream().sorted(comparator).distinct().collect(Collectors.toList());
    }

    /**
     * 排序集合
     * @param collection 集合对象
     * @param comparator 比较器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 排序后的集合
     */
    public static <T extends Collection<E>,E> Set<E> sortedToSet(T collection,Comparator<E> comparator){
        return collection.stream().sorted(comparator).collect(Collectors.toSet());
    }

    /**
     * 集合转换泛型
     * @param collection 集合对象
     * @param clazz 要转换的类型
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 转换后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> mapToList(T collection, Class<A> clazz){
        return collection.stream().map(v -> {
            try {
                return BeanUtil.copyProperties(v, clazz);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).collect(Collectors.toList());
    }

    /**
     * 集合转换泛型并去重
     * @param collection 集合对象
     * @param clazz 要转换的类型
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 转换后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> mapDistToList(T collection, Class<A> clazz){
        return collection.stream().map(v -> {
            try {
                return BeanUtil.copyProperties(v, clazz);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).distinct().collect(Collectors.toList());
    }

    /**
     * 集合转换泛型并
     * @param collection 集合对象
     * @param clazz 要转换的类型
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 转换后的集合
     */
    public static <T extends Collection<E>,E,A> Set<A> mapToSet(T collection, Class<A> clazz){
        return collection.stream().map(v -> {
            try {
                return BeanUtil.copyProperties(v, clazz);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).distinct().collect(Collectors.toSet());
    }

    /**
     * 集合转换泛型
     * 基于映射器转换泛型
     * @param collection 集合对象
     * @param mapper 映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 转换后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> mapToList(T collection, Function<E, A> mapper){
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合转换泛型
     * 基于映射器转换泛型
     * @param collection 集合对象
     * @param mapper 映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 转换后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> mapDistToList(T collection, Function<E, A> mapper){
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合转换泛型
     * 基于映射器转换泛型
     * @param collection 集合对象
     * @param mapper 映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 转换后的集合
     */
    public static <T extends Collection<E>,E,A> Set<A> mapToSet(T collection, Function<E, A> mapper){
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    /**
     * 集合过滤后转换泛型
     * @param collection 集合对象
     * @param clazz 要转换的类型
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> filterMapToList(T collection, Predicate<E> predicate, Class<A> clazz){
        return collection.stream().filter(predicate).map(v -> {
            try {
                return BeanUtil.copyProperties(v, clazz);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("类型转换失败,不能从类型[" + v.getClass().getTypeName() + "]转换到[" + clazz.getTypeName() + "]类型");
            }
        }).collect(Collectors.toList());
    }

    /**
     * 集合过滤后转换泛型并去重
     * @param collection 集合对象
     * @param clazz 要转换的类型
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 过滤转换去重后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> filterMapDistToList(T collection, Predicate<E> predicate, Class<A> clazz){
        return collection.stream().filter(predicate).map(v -> {
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
     * @param collection 集合对象
     * @param clazz 要转换的类型
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public static <T extends Collection<E>,E,A> Set<A> filterMapToSet(T collection, Predicate<E> predicate, Class<A> clazz){
        return collection.stream().filter(predicate).map(v -> {
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
     * @param collection 集合对象
     * @param mapper 映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> filterMapToList(T collection, Predicate<E> predicate, Function<E, A> mapper){
        return collection.stream().filter(predicate).map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合过滤后转换泛型
     * @param collection 集合对象
     * @param mapper 映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public static <T extends Collection<E>,E,A> List<A> filterMapDistToList(T collection, Predicate<E> predicate, Function<E, A> mapper){
        return collection.stream().filter(predicate).map(mapper).distinct().collect(Collectors.toList());
    }


    /**
     * 集合过滤后转换泛型
     * @param collection 集合对象
     * @param mapper 映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 过滤转换后的集合
     */
    public static <T extends Collection<E>,E,A> Set<A> filterMapToSet(T collection, Predicate<E> predicate, Function<E, A> mapper){
        return collection.stream().filter(predicate).map(mapper).collect(Collectors.toSet());
    }

    /**
     * 判断集合中所有元素是否全部满足条件
     * @param collection 集合对象
     * @param predicate 匹配器匹配器
     * @param isAll true:全部匹配 false:全部不匹配
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return true:全部满足条件 false:有元素不满足条件
     */
    public static <T extends Collection<E>,E> boolean match(T collection, Predicate<E> predicate,boolean isAll){
        return isAll ? collection.stream().allMatch(predicate) : collection.stream().noneMatch(predicate);
    }

    /**
     * 获取当前集合中最大值元素
     * @param collection 集合对象
     * @param comparator 比较器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 集合中元素比较后的最大值对象
     * @throws RuntimeException 该方法在取不到最大值或最大值为Null的情况下抛出RuntimeException:获取当前集合中比较后元素最大值异常
     */
    public static <T extends Collection<E>,E> E max(T collection,Comparator<E> comparator) throws RuntimeException{
        return collection.stream().max(comparator).orElseThrow(() -> new RuntimeException("获取当前集合中比较后元素最大值异常"));
    }

    /**
     * 获取当前集合中最小值元素
     * @param collection 集合对象
     * @param comparator 比较器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 集合中元素比较后的最小值对象
     * @throws RuntimeException 该方法在取不到最小值或最小值为Null的情况下抛出RuntimeException:获取当前集合中比较后元素最小值异常
     */
    public static <T extends Collection<E>,E> E min(T collection,Comparator<E> comparator) throws RuntimeException{
        return collection.stream().min(comparator).orElseThrow(() -> new RuntimeException("获取当前集合中比较后元素最小值异常"));
    }

    /**
     * 随机获取当前集合中一个元素
     * @param collection 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 当前流中随机一个元素
     * @throws RuntimeException 该方法在取不到元素或取到元素为Null的情况下抛出RuntimeException:随机获取当前集合中一个元素异常
     */
    public static <T extends Collection<E>,E> E findAny(T collection)throws RuntimeException{
        return collection.stream().findAny().orElseThrow(() -> new RuntimeException("随机获取当前集合中一个元素异常"));
    }

    /**
     * 随机获取当前流中一个元素
     * @param stream Stream流对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 当前流中随机一个元素
     * @throws RuntimeException 该方法在取不到元素或取到元素为Null的情况下抛出RuntimeException:随机获取当前集合中一个元素异常
     */
    public static <T extends Collection<E>,E> E findAny(Stream<E> stream)throws RuntimeException{
        return stream.findAny().orElseThrow(() -> new RuntimeException("随机获取当前集合中一个元素异常"));
    }
    
    /**
     * 获取当前集合过滤后元素数量
     * @param collection 集合对象
     * @param predicate 匹配器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 过滤后元素数量
     */
    @NotNull
    public  static <T extends Collection<E>,E> long filterCount(T collection, Predicate<E> predicate){
        return collection.stream().filter(predicate).count();
    }

    /**
     * 获取当前集合过滤去重后元素数量
     * @param collection 集合对象
     * @param predicate 匹配器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 过滤去重后元素数量
     */
    public  static <T extends Collection<E>,E> long filterDistCount(T collection, Predicate<E> predicate){
        return collection.stream().filter(predicate).distinct().count();
    }
    
    /**
     * 根据集合元素某个字段进行分组
     * @param collection 集合对象
     * @param classifier 类映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 分组后集合 Map<K,V> K:作为分组条件的字段 V:根据条件分组后的对象集合
     */
    public static <T extends Collection<E>,E> Map<Object, List<E>> groupBy(T collection,Function<E,Object> classifier){
        return collection.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * 去重后根据集合元素某个字段进行分组
     * @param collection 集合对象
     * @param classifier 类映射器
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 去重分组后集合 Map<K,V> K:作为分组条件的字段 V:根据条件分组后的对象集合
     */
    public static <T extends Collection<E>,E> Map<Object, List<E>> groupByDist(T collection,Function<E,Object> classifier){
        return collection.stream().distinct().collect(Collectors.groupingBy(classifier));
    }
    
    /**
     * 合并多个集合,返回新集合
     * @param collections 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 合并后的新集合
     */
    public static <T extends Collection<E>,E> List<E> mergeToList(T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * 合并多个集合,并去重,返回新集合
     * @param collections 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 合并去重后的新集合
     */
    public static <T extends Collection<E>,E> List<E> mergeAsDistToList(T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,返回新集合
     * @param predicate 匹配器
     * @param collections 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 合并过滤后的新集合
     */
    public static <T extends Collection<E>,E> List<E> mergeToList(Predicate<E> predicate, T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,去重,返回新集合
     * @param predicate 匹配器
     * @param collections 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 合并过滤后去重的新集合
     */
    public static <T extends Collection<E>,E> List<E> mergeDistToList(Predicate<E> predicate, T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).distinct().collect(Collectors.toList());
    }
    
    /**
     * 合并多个集合,过滤后,进行泛型转换,然后返回新集合
     * 基于映射器转换泛型
     * @param predicate 匹配器
     * @param mapper 映射器
     * @param collections 集合对象数组
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 合并过滤泛型转换后的新集合
     */
    public static <T extends Collection<E>,E,A> List<A> mergeToList(Predicate<E> predicate,Function<E,A> mapper,T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).map(mapper).collect(Collectors.toList());
    }
    
    /**
     * 合并多个集合,过滤后,进行泛型转换,去重,然后返回新集合
     * 基于映射器转换泛型
     * @param predicate 匹配器
     * @param mapper 映射器
     * @param collections 集合对象数组
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 合并过滤泛型转换去重后的新集合
     */
    public static <T extends Collection<E>,E,A> List<A> mergeDistToList(Predicate<E> predicate,Function<E,A> mapper,T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).map(mapper).distinct().collect(Collectors.toList());
    }

    /**
     * 合并多个集合,过滤后,进行泛型转换,然后返回新集合
     * 基于映射器转换泛型
     * @param predicate 匹配器
     * @param mapper 映射器
     * @param collections 集合对象数组
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @param <A> 转换后集合元素泛型
     * @return 合并过滤泛型转换后的新集合
     */
    public static <T extends Collection<E>,E,A> Set<A> mergeToSet(Predicate<E> predicate,Function<E,A> mapper,T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).map(mapper).collect(Collectors.toSet());
    }

    /**
     * 合并多个集合,返回新集合
     * @param collections 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 合并后的新集合
     */
    public static <T extends Collection<E>,E> Set<E> mergeToSet(T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    /**
     * 合并多个集合,过滤后,返回新集合
     * @param predicate 匹配器
     * @param collections 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 合并过滤后的新集合
     */
    public static <T extends Collection<E>,E> Set<E> mergeToSet(Predicate<E> predicate, T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).filter(predicate).collect(Collectors.toSet());
    }

    /**
     * 合并多个集合,并去重,返回新集合
     * @param collections 集合对象
     * @param <T> 泛型,继承Collection
     * @param <E> 集合元素泛型
     * @return 合并去重后的新集合
     */
    public static <T extends Collection<E>,E> Set<E> mergeAsDistToSet(T... collections){
        return Arrays.stream(collections).flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
