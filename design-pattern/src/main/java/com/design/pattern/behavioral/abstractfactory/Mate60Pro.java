package com.design.pattern.behavioral.abstractfactory;

/**
 * 华为手机类
 */
public class Mate60Pro implements AbstractPhone{

    @Override
    public void getBrand() {
        System.out.println("HUAWEI Mate 60 Pro 1TB 冷锋蓝(遥遥领先)");;
    }
}
