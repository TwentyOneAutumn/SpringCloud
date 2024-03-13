package com.design.pattern.behavioral.abstractfactory;

/**
 * 苹果手机类
 */
public class IPhone implements AbstractPhone {

    @Override
    public void getBrand() {
        System.out.println("苹果15ProMax 1TB 远峰蓝");
    }
}
