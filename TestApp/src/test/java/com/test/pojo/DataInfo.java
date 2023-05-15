package com.test.pojo;

import com.core.Interface.Import;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataInfo {


    /**
     * 机型代码
     */
    @Import("ciaoCode")
    private String ciaoCode;


    /**
     * 主轮距
     */
    @Import("wheelTrack")
    private String wheelTrack;


    /**
     * 翼展
     */
    @Import("wingspan")
    private String wingspan;



    /**
     * 机身长度
     */
    @Import("fuselageLength")
    private String fuselageLength;


    /**
     * 最大起飞重量
     */
    @Import("mtow")
    private String mtow;


    /**
     * 最大着陆重量
     */
    @Import("mlw")
    private String mlw;


    /**
     * 升限
     */
    @Import("ceiling")
    private String ceiling;


    /**
     * 正常巡航速度(马赫)
     */
    @Import("normalCruiseSpeedM")
    private String normalCruiseSpeedM;


    /**
     * 发动机类型
     */
    @Import("engineType")
    private String engineType;


    /**
     * 发动机数量
     */
    @Import("engineNum")
    private String engineNum;


    /**
     * 尾流等级
     */
    @Import("wtc")
    private String wtc;
}
