package com.core.utils;

import cn.hutool.core.collection.CollUtil;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.LinkedList;

/**
 * 坐标工具类
 */
public class GisPoint {

    /**
     * 缓存坐标点
     */
    private final LinkedList<Point2D.Double> pointList = new LinkedList<>();

    /**
     * 创建对象
     * 构建需要传入需要验证点的经度和纬度
     * @return GisPoint
     */
    public static GisPoint create(){
        return new GisPoint();
    }

    /**
     * 添加坐标点
     */
    public void lineTo(double longitude, double latitude){
        this.pointList.addLast(new Point2D.Double(longitude,latitude));
    }

    /**
     * 添加坐标点
     */
    public void lineTo(String longitude, String latitude){
        if(longitude.length() > 7){
            longitude = longitude.substring(0, 7);
        }
        if(latitude.length() > 6){
            latitude = latitude.substring(0, 6);
        }
        this.pointList.addLast(dmsToDecimalDegrees(longitude,latitude));
    }

    /**
     * 添加坐标点
     */
    public void lineTo(Point2D.Double point){
        this.pointList.addLast(point);
    }

    /**
     * 添加一组坐标点
     * @param pointList 坐标点集合
     */
    public void addLines(LinkedList<Point2D.Double> pointList){
        if(CollUtil.isNotEmpty(pointList)){
            pointList.forEach(this.pointList::addLast);
        }
    }

    /**
     * 格式化经纬度
     * @param longitude 经度
     * @param latitude 纬度
     * @return 坐标点
     */
    private Point2D.Double dmsToDecimalDegrees(String longitude,String latitude) {
        return new Point2D.Double(Integer.parseInt(longitude.substring(0,3)) + (Integer.parseInt(longitude.substring(3,5)) / 60.0) + (Integer.parseInt(longitude.substring(5,7)) / 3600.0),Integer.parseInt(latitude.substring(0,2)) + (Integer.parseInt(latitude.substring(2,4)) / 60.0) + (Integer.parseInt(latitude.substring(4,6)) / 3600.0));
    }

    /**
     * 验证点是否在构建的图形内
     */
    public boolean isInside(String longitude, String latitude) {
        // 创建图形对象
        GeneralPath generalPath = new GeneralPath();

        // 转化为LinkedList
        // 初始化点
        Point2D.Double initPoint = pointList.removeFirst();
        // 初始化初始点
        generalPath.moveTo(initPoint.x,initPoint.y);

        // 循环添加剩余坐标点
        pointList.forEach(point -> generalPath.lineTo(point.x,point.y));

        // 闭合路径
        generalPath.closePath();

        // 判断点是否在多边形内
        return generalPath.contains(dmsToDecimalDegrees(longitude,latitude));
    }

    /**
     * 验证点是否在构建的图形内
     */
    public boolean isInside(double longitude, double latitude) {
        // 创建图形对象
        GeneralPath generalPath = new GeneralPath();

        // 转化为LinkedList
        // 初始化点
        Point2D.Double initPoint = pointList.removeFirst();
        // 初始化初始点
        generalPath.moveTo(initPoint.x,initPoint.y);

        // 循环添加剩余坐标点
        pointList.forEach(point -> generalPath.lineTo(point.x,point.y));

        // 闭合路径
        generalPath.closePath();

        // 判断点是否在多边形内
        return generalPath.contains(longitude, latitude);
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
