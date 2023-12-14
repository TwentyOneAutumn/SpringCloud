package com.core.utils;

/**
 * 纬度工具类
 */
public class LongitudeUtils {

    /**
     * 格式化纬度
     * @param longitude 纬度
     * @return 格式化后的纬度
     */
    public static String format(double longitude){
        String lonDirection = (longitude >= 0) ? "E" : "W";
        longitude = Math.abs(longitude);
        int lonDegrees = (int) longitude;
        longitude = (longitude - lonDegrees) * 60;
        int lonMinutes = (int) longitude;
        longitude = (longitude - lonMinutes) * 60;
        int lonSeconds = (int) longitude;
        return String.format("%s%d°%02d′%02d″",lonDirection, lonDegrees, lonMinutes, lonSeconds);
    }
}
