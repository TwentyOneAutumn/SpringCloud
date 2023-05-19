package com.core.utils;

import com.core.enums.CompareType;
import com.core.enums.TimeUnit;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 将Date转换为LocalDateTime，时区为系统默认时区
     * @param time Date对象
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date time){
        return time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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
     * 计算两个时间的差异并转化为响应的单位
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
}
