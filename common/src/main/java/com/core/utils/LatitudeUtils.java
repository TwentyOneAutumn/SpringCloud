package com.core.utils;

/**
 * 经度工具类
 */
public class LatitudeUtils {

    /**
     * 格式化经度
     * @param latitude 经度
     * @return 格式化后的经度
     */
    public static String format(double latitude){
        String latDirection = (latitude >= 0) ? "N" : "S";
        latitude = Math.abs(latitude);
        int latDegrees = (int) latitude;
        latitude = (latitude - latDegrees) * 60;
        int latMinutes = (int) latitude;
        latitude = (latitude - latMinutes) * 60;
        int latSeconds = (int) latitude;
        return String.format("%s%d°%02d′%02d″",latDirection, latDegrees, latMinutes, latSeconds);
    }
}
