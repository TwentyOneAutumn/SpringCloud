package com.core.utils;

import cn.hutool.core.bean.BeanUtil;

import java.awt.geom.GeneralPath;

/**
 * 坐标工具类
 */
public class GisPointUtil {

    /**
     * 验证点经度
     */
    private Double checkLongitude;

    /**
     * 验证点纬度
     */
    private Double checkLatitude;

    /**
     * 第一个坐标点经度
     */
    private Double firstLongitude;

    /**
     * 第一个坐标点纬度
     */
    private Double firstLatitude;

    /**
     * 图形路径对象
     */
    private final GeneralPath generalPath = new GeneralPath();

    /**
     * 构造
     * @param latitude 验证点经度
     * @param longitude 验证点纬度
     */
    private GisPointUtil(Double latitude, Double longitude){
        this.checkLongitude = longitude;
        this.checkLatitude = latitude;
    }

    /**
     * 创建对象
     * 构建需要传入需要验证点的经度和纬度
     * @param latitude 经度
     * @param longitude 纬度
     * @return GisPointUtil
     */
    public static GisPointUtil create(Double latitude, Double longitude){
        return new GisPointUtil(latitude,longitude);
    }

    /**
     * 创建对象
     * 构建需要传入需要验证点的经度和纬度
     * @param latitude 经度
     * @param longitude 纬度
     * @return GisPointUtil
     */
    public static GisPointUtil create(Integer longitude, Integer latitude){
        return GisPointUtil.create(Double.valueOf(longitude),Double.valueOf(latitude));
    }

    /**
     * 添加多边形坐标点
     * @param longitude 经度
     * @param latitude 纬度
     * @return GisPointUtil
     */
    public GisPointUtil lineTo(Double longitude, Double latitude){
        if(BeanUtil.isEmpty(this.firstLongitude) || BeanUtil.isEmpty(this.firstLatitude)){
            this.firstLongitude = longitude;
            this.firstLatitude = latitude;
            this.generalPath.moveTo(this.firstLongitude,this.firstLatitude);
        }else {
            this.generalPath.lineTo(longitude,latitude);
        }
        return this;
    }

    /**
     * 添加多边形坐标点
     * @param longitude 经度
     * @param latitude 纬度
     * @return GisPointUtil
     */
    public GisPointUtil lineTo(Integer longitude, Integer latitude){
        lineTo(Double.valueOf(longitude),Double.valueOf(latitude));
        return this;
    }

    /**
     * 验证点是否在构建的图形内
     */
    public boolean isInside() {
        // 添加初始坐标点
        this.generalPath.lineTo(this.firstLongitude,this.firstLatitude);

        // 闭合路径
        this.generalPath.closePath();

        // 判断点是否在多边形内
        return this.generalPath.contains(this.checkLongitude, this.checkLatitude);
    }

    /**
     * 格式化经度
     * @param longitude 经度
     * @return 格式化后的经度
     */
    public static String formatLon(double longitude){
        String lonDirection = (longitude >= 0) ? "E" : "W";
        longitude = Math.abs(longitude);
        int lonDegrees = (int) longitude;
        longitude = (longitude - lonDegrees) * 60;
        int lonMinutes = (int) longitude;
        longitude = (longitude - lonMinutes) * 60;
        int lonSeconds = (int) longitude;
        return String.format("%s%d°%02d′%02d″",lonDirection, lonDegrees, lonMinutes, lonSeconds);
    }

    /**
     * 格式化纬度
     * @param latitude 纬度
     * @return 格式化后的纬度
     */
    public static String formatLat(double latitude){
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
