package com.core.utils;

import cn.hutool.core.collection.CollUtil;
import com.core.domain.MapEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 合并时间段
     */
    public static List<MapEntry<LocalDateTime,LocalDateTime>> mergeLocalDate(List<MapEntry<LocalDateTime,LocalDateTime>> timeList){
        // 储存需要删除的元素
        List<MapEntry<LocalDateTime, LocalDateTime>> deletedTimePeriods = new LinkedList<>();
        // 储存合并后的元素
        LinkedList<MapEntry<LocalDateTime, LocalDateTime>> mergeTimePeriods = new LinkedList<>();
        // 排序后得到时间段桶
        LinkedList<MapEntry<LocalDateTime, LocalDateTime>> timePeriods = timeList.stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toCollection(LinkedList::new));
        // 循环处理
        while (CollUtil.isNotEmpty(timePeriods)){
            // 删除时间段桶中的第一个元素
            MapEntry<LocalDateTime, LocalDateTime> first = timePeriods.removeFirst();
            LocalDateTime firstKey = first.getKey();
            LocalDateTime firstValue = first.getValue();
            // 循环进行时间合并
            if (!timePeriods.isEmpty()){
                timePeriods.forEach(periods -> {
                    LocalDateTime periodsKey = periods.getKey();
                    LocalDateTime periodsValue = periods.getValue();
                    if(isLocalDateTimeOverlap(first,periods)){
                        // 添加当前元素到待删除集合中
                        deletedTimePeriods.add(periods);
                        // 合并时间段
                        LocalDateTime startTime = smaller(firstKey,periodsKey);
                        LocalDateTime endTime = bigger(firstValue,periodsValue);
                        first.setKey(startTime);
                        first.setValue(endTime);
                    }
                });
                // 判空
                if(CollUtil.isNotEmpty(deletedTimePeriods)){
                    // 删除已经合并的元素
                    timePeriods.removeAll(deletedTimePeriods);
                }
            }
            // 添加合并后的时间段
            mergeTimePeriods.add(first);
        }
        // 返回合并后的时间段桶
        return mergeTimePeriods;
    }


    /**
     * 合并时间段
     */
    public static List<MapEntry<LocalDate,LocalDate>> mergeLocalDateTime(List<MapEntry<LocalDate,LocalDate>> timeList){
        // 储存需要删除的元素
        List<MapEntry<LocalDate, LocalDate>> deletedTimePeriods = new LinkedList<>();
        // 储存合并后的元素
        LinkedList<MapEntry<LocalDate, LocalDate>> mergeTimePeriods = new LinkedList<>();
        // 排序后得到时间段桶
        LinkedList<MapEntry<LocalDate, LocalDate>> timePeriods = timeList.stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toCollection(LinkedList::new));
        // 循环处理
        while (CollUtil.isNotEmpty(timePeriods)){
            // 删除时间段桶中的第一个元素
            MapEntry<LocalDate, LocalDate> first = timePeriods.removeFirst();
            LocalDate firstKey = first.getKey();
            LocalDate firstValue = first.getValue();
            // 循环进行时间合并
            if (!timePeriods.isEmpty()){
                timePeriods.forEach(periods -> {
                    LocalDate periodsKey = periods.getKey();
                    LocalDate periodsValue = periods.getValue();
                    if(isLocalDateOverlap(first,periods)){
                        // 添加当前元素到待删除集合中
                        deletedTimePeriods.add(periods);
                        // 合并时间段
                        LocalDate startTime = smaller(firstKey,periodsKey);
                        LocalDate endTime = bigger(firstValue,periodsValue);
                        first.setKey(startTime);
                        first.setValue(endTime);
                    }
                });
                // 判空
                if(CollUtil.isNotEmpty(deletedTimePeriods)){
                    // 删除已经合并的元素
                    timePeriods.removeAll(deletedTimePeriods);
                }
            }
            // 添加合并后的时间段
            mergeTimePeriods.add(first);
        }
        // 返回合并后的时间段桶
        return mergeTimePeriods;
    }


    /**
     * 判断两段时间是否重合
     * @return true:重合 false:不重合
     */
    public static boolean isLocalDateTimeOverlap(MapEntry<LocalDateTime,LocalDateTime> firstEntry,MapEntry<LocalDateTime,LocalDateTime> lastEntry){
        LocalDateTime firstEntryKey = firstEntry.getKey();
        LocalDateTime firstEntryValue = firstEntry.getValue();
        LocalDateTime lastEntryKey = lastEntry.getKey();
        LocalDateTime lastEntryValue = lastEntry.getValue();
        return (!firstEntryKey.isAfter(lastEntryKey) && !firstEntryValue.isBefore(lastEntryValue)) || (!lastEntryKey.isAfter(firstEntryKey) &&  !lastEntryValue.isBefore(firstEntryValue)) || (firstEntryKey.isBefore(lastEntryKey) ? !firstEntryValue.isBefore(lastEntryKey) : !lastEntryValue.isBefore(firstEntryKey));
    }


    /**
     * 判断两段时间是否重合
     * @return true:重合 false:不重合
     */
    public static boolean isLocalDateOverlap(MapEntry<LocalDate,LocalDate> firstLocalDateEntry,MapEntry<LocalDate,LocalDate> lastLocalDateEntry){
        LocalDate firstEntryKey = firstLocalDateEntry.getKey();
        LocalDate firstEntryValue = firstLocalDateEntry.getValue();
        LocalDate lastEntryKey = lastLocalDateEntry.getKey();
        LocalDate lastEntryValue = lastLocalDateEntry.getValue();
        return (!firstEntryKey.isAfter(lastEntryKey) && !firstEntryValue.isBefore(lastEntryValue)) || (!lastEntryKey.isAfter(firstEntryKey) &&  !lastEntryValue.isBefore(firstEntryValue)) || (firstEntryKey.isBefore(lastEntryKey) ? !firstEntryValue.isBefore(lastEntryKey) : !lastEntryValue.isBefore(firstEntryKey));
    }


    /**
     * 获取两段时间中比较大的一个
     */
    public static LocalDate bigger(LocalDate time,LocalDate currentTime){
        return time.isBefore(currentTime) ? time : currentTime;
    }


    /**
     * 获取两段时间中比较大的一个
     */
    public static LocalDateTime bigger(LocalDateTime time,LocalDateTime currentTime){
        return time.isBefore(currentTime) ? time : currentTime;
    }


    /**
     * 获取两段时间中比较小的一个
     */
    public static LocalDate smaller(LocalDate time,LocalDate currentTime){
        return time.isAfter(currentTime) ? time : currentTime;
    }


    /**
     * 获取两段时间中比较小的一个
     */
    public static LocalDateTime smaller(LocalDateTime time,LocalDateTime currentTime){
        return time.isAfter(currentTime) ? time : currentTime;
    }
}
