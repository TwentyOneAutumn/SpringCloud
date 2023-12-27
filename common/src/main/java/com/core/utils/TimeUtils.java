package com.core.utils;

import cn.hutool.core.collection.CollUtil;
import com.core.enums.CompareType;
import com.core.enums.TimeUnit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 将Date转换为LocalDateTime，时区为默认为中国上海时区
     * @param time Date对象
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date time){
        return time.toInstant().atZone(ZoneId.of("CTT")).toLocalDateTime();
    }

    /**
     * 将日期字符串转换为LocalDateTime，时区为默认为中国上海时区
     * @param time 时间字符串
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String time) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).toInstant().atZone(ZoneId.of("CTT")).toLocalDateTime();
    }

    /**
     * 将日期字符串转换为LocalDate，时区为默认为中国上海时区
     * @param time 时间字符串
     * @return LocalDate
     */
    public static LocalDate toLocalDate(String time) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).toInstant().atZone(ZoneId.of("CTT")).toLocalDate();
    }

    /**
     * 将日期字符串转换为Date
     * @param time 时间字符串
     * @return Date
     */
    public static Date toDate(String time) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
    }

    /**
     * 将日期字符串转换为Date
     * @param time 时间字符串
     * @param format 格式
     * @return Date
     */
    public static Date toDate(String time,String format) throws ParseException {
        return new SimpleDateFormat(format).parse(time);
    }

    /**
     * 将LocalDate转换为LocalDateTime，默认为午夜00:00:00
     * @param time LocalDate对象
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(LocalDate time){
        return time.atStartOfDay();
    }

    /**
     * 计算两个时间的差异并转化为相应的时间单位
     * @param startTime 时间参数
     * @param endTime 时间参数
     * @param timeUnit 时间单位，参考{@link TimeUnit}
     * @return long
     */
    public static long diff(Temporal startTime, Temporal endTime, String timeUnit){
        Duration duration = Duration.between(startTime, endTime);
        switch (timeUnit){
            case TimeUnit.NanoSecond : {
                return duration.toNanos();
            }
            case TimeUnit.MilliSecond : {
                return duration.toMillis();
            }
            case TimeUnit.Second : {
                return duration.getSeconds();
            }
            case TimeUnit.Minute : {
                return duration.toMinutes();
            }
            case TimeUnit.Day : {
                return duration.toDays();
            }
            default:{
                throw new RuntimeException("");
            }
        }
    }

    /**
     * 计算两个时间的差异并转化为响应的单位
     * @param startTime 时间参数
     * @param endTime 时间参数
     * @param timeUnit 时间单位，参考 {@link TimeUnit}
     * @return long
     */
    public static long diff(Date startTime, Date endTime, String timeUnit){
        return diff(toLocalDateTime(startTime),toLocalDateTime(endTime),timeUnit);
    }

    /**
     * 计算两个时间的比较结果
     * @param startTime 时间参数
     * @param endTime 时间参数
     * @param compare 比较方式，参考 {@link CompareType}
     * @return 比较结果
     */
    public static boolean compareTo(Temporal startTime, Temporal endTime,String compare){
        int diff = 0;
        if(startTime instanceof LocalDateTime){
            diff = ((LocalDateTime) startTime).compareTo((LocalDateTime) endTime);
        }
        else if(startTime instanceof LocalDate){
            diff = ((LocalDate) startTime).compareTo((LocalDate) endTime);
        }else {
            throw new RuntimeException("");
        }
        switch (compare){
            case CompareType.GT : {
                return diff > 0;
            }
            case CompareType.LT : {
                return diff < 0;
            }
            case CompareType.EQ : {
                return diff == 0;
            }
            case CompareType.GE : {
                return diff >= 0;
            }
            case CompareType.LE : {
                return diff <= 0;
            }
            default : {
                throw new RuntimeException("");
            }
        }
    }

    /**
     * 计算两个时间的比较结果
     * @param startTime 时间参数
     * @param endTime 时间参数
     * @param compare 比较方式，参考 {@link CompareType}
     * @return 比较结果
     */
    public static boolean compareTo(Date startTime, Date endTime,String compare){
        return compareTo(toLocalDateTime(startTime),toLocalDateTime(endTime),compare);
    }

    /**
     * 添加或者减少天数，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDate
     */
    public static LocalDate figureUpDay(LocalDate time,long millis){
        return time.plusDays(millis);
    }

    /**
     * 添加或者减少月数，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDate
     */
    public static LocalDate figureUpMonth(LocalDate time,long millis){
        return time.plusMonths(millis);
    }

    /**
     * 添加或者减少年，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDate
     */
    public static LocalDate figureUpYear(LocalDate time,long millis){
        return time.plusYears(millis);
    }

    /**
     * 添加或者减少周，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDate
     */
    public static LocalDate figureUpWeek(LocalDate time,long millis){
        return time.plusWeeks(millis);
    }

    /**
     * 添加或者减少天数，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDateTime
     */
    public static LocalDateTime figureUpDay(LocalDateTime time,long millis){
        return time.plusDays(millis);
    }

    /**
     * 添加或者减少月数，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDateTime
     */
    public static LocalDateTime figureUpMonth(LocalDateTime time,long millis){
        return time.plusMonths(millis);
    }

    /**
     * 添加或者减少年，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDateTime
     */
    public static LocalDateTime figureUpYear(LocalDateTime time,long millis){
        return time.plusYears(millis);
    }

    /**
     * 添加或者减少周，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDateTime
     */
    public static LocalDateTime figureUpWeek(LocalDateTime time,long millis){
        return time.plusWeeks(millis);
    }

    /**
     * 添加或者减少小时，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDateTime
     */
    public static LocalDateTime figureUpHour(LocalDateTime time,long millis){
        return time.plusHours(millis);
    }

    /**
     * 添加或者减少分钟，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDateTime
     */
    public static LocalDateTime figureUpMinute(LocalDateTime time,long millis){
        return time.plusMinutes(millis);
    }

    /**
     * 添加或者减少秒，并修改因此影响到的其他日期单位
     * @param time 时间对象
     * @param millis 正数代表增加，负数代表减少
     * @return LocalDateTime
     */
    public static LocalDateTime figureUpSecond(LocalDateTime time,long millis){
        return time.plusSeconds(millis);
    }

//    public static Map<LocalDateTime,LocalDateTime> calculateTotalDuration(Map<LocalDateTime,LocalDateTime> timeMap){
//        if(CollUtil.isEmpty(timeMap)){
//            return timeMap;
//        }
//        Set<Map.Entry<LocalDateTime, LocalDateTime>> set = timeMap.entrySet();
//        // 根据是否有重叠时间进行分组
//        set.stream().collect(Collectors.partitioningBy(entry -> {
//            set.stream().anyMatch()
//        }));
//    }
//
//    public static boolean isOverlap(Map.Entry<LocalDateTime, LocalDateTime> time,Map.Entry<LocalDateTime, LocalDateTime> checkTime){
//        LocalDateTime startTime = time.getKey().withNano(0);
//        LocalDateTime endTime = time.getValue().withNano(0);
//        LocalDateTime checkStartTime = checkTime.getKey().withNano(0);
//        LocalDateTime checkEndTime = checkTime.getValue().withNano(0);
//        return startTime.isEqual(checkStartTime) || startTime.isEqual(checkEndTime) || endTime.equals(checkStartTime) || endTime.equals(checkEndTime) ||
//                (startTime.isBefore(checkStartTime)  && endTime.isAfter(checkEndTime)) ||
//                (checkStartTime.isBefore(startTime)  && checkEndTime.isAfter(endTime)) ||
//                (startTime.isBefore(checkStartTime)  && endTime.isAfter(checkStartTime)) ||
//    }

//    public static <T extends Temporal> long calculateTotalDuration(Map<T,T> timeMap){
//        if(CollUtil.){
//
//        }
//        Set<Map.Entry<T, T>> set = timeMap.entrySet();
//        // 根据是否有重叠时间进行分组
//        set
//    }
}
