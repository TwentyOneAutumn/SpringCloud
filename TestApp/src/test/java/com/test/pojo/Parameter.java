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
    private Integer engineNum;


    /**
     * 机身长度
     */
    private Double fuselageLength;


    /**
     * 机高
     */
    private Double fuselageHeight;


    /**
     * 翼展
     */
    private Double wingspan;


    /**
     * 最大起飞重量
     */
    private Double mtow;


    /**
     * 最大着陆重量
     */
    private Double mlw;


    /**
     * 最大业载
     */
    private Double maximumLoad;


    /**
     * 空重
     */
    private Double emptyWeight;


    /**
     * 最大爬升率
     */
    private Double maxClimbRate;


    /**
     * 最大下降率
     */
    private Double maxDescentRate;


    /**
     * 起飞距离
     */
    private Double takeOff;


    /**
     * 着陆距离
     */
    private Double landOff;


    /**
     * 实用升限
     */
    private Double hmo;


    /**
     * 尾流等级
     */
    private Double wtc;


    /**
     * 最大飞行速度
     */
    private Double vmo;


    /**
     * 最大飞行马赫数
     */
    private Double mmo;


    /**
     * 正常巡航速度(节)
     */
    private Double normalCruiseSpeed;


    /**
     * 正常巡航速度(马赫)
     */
    private Double normalCruiseSpeedM;


    /**
     * 经济巡航速度
     */
    private Double economyCruiseSpeed;


    /**
     * 最小光洁速度
     */
    private Double minCrFinshingSpeed;


    /**
     * 升限分类
     */
    private String ceilingClassify;


    /**
     * 跑道入口速度
     */
    private Double runwayInletSpeed;


    /**
     * 进近性能类型
     */
    private String apType;


    /**
     * 起飞噪声
     */
    private Double takeoffNoise;


    /**
     * 进近噪声
     */
    private Double approachNoise;


    /**
     * 横向噪声
     */
    private Double wichosNoise;


    /**
     * 主轮距
     */
    private Double wheelTrack;
}
