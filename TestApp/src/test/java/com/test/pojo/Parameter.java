package com.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter implements Serializable {


    /**
     * 机型代码
     */
    private String ciaoCode;


    /**
     * 飞机制造商
     */
    private String airplaneMaker;


    /**
     * 航空器类型
     */
    private String aircraftType;


    /**
     * 发动机类型
     */
    private String engineType;


    /**
     * 发动机数量
     */
    private String engineNum;


    /**
     * 机身长度
     */
    private String fuselageLength;


    /**
     * 机高
     */
    private String fuselageHeight;


    /**
     * 翼展
     */
    private String wingspan;


    /**
     * 最大起飞重量
     */
    private String mtow;


    /**
     * 最大着陆重量
     */
    private String mlw;


    /**
     * 最大业载
     */
    private String maximumLoad;


    /**
     * 空重
     */
    private String emptyWeight;


    /**
     * 最大爬升率
     */
    private String maxClimbRate;


    /**
     * 最大下降率
     */
    private String maxDescentRate;


    /**
     * 起飞距离
     */
    private String takeOff;


    /**
     * 着陆距离
     */
    private String landOff;


    /**
     * 实用升限
     */
    private String hmo;


    /**
     * 尾流等级
     */
    private String wtc;


    /**
     * 最大飞行速度
     */
    private String vmo;


    /**
     * 最大飞行马赫数
     */
    private String mmo;


    /**
     * 正常巡航速度(节)
     */
    private String normalCruiseSpeed;


    /**
     * 正常巡航速度(马赫)
     */
    private String normalCruiseSpeedM;


    /**
     * 经济巡航速度
     */
    private String economyCruiseSpeed;


    /**
     * 最小光洁速度
     */
    private String minCrFinshingSpeed;


    /**
     * 升限分类
     */
    private String ceilingClassify;


    /**
     * 跑道入口速度
     */
    private String runwayInletSpeed;


    /**
     * 进近性能类型
     */
    private String apType;


    /**
     * 起飞噪声
     */
    private String takeoffNoise;


    /**
     * 进近噪声
     */
    private String approachNoise;


    /**
     * 横向噪声
     */
    private String wichosNoise;


    /**
     * 主轮距
     */
    private String wheelTrack;
}
