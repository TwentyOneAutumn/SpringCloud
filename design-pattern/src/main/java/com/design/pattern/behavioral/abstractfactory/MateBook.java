package com.design.pattern.behavioral.abstractfactory;

/**
 * 华为电脑类
 */
public class MateBook implements AbstractComputer{

    @Override
    public void getSystemType() {
         System.out.println("HUAWEI MateBook 32G 2T (遥遥领先)");
    }
}
