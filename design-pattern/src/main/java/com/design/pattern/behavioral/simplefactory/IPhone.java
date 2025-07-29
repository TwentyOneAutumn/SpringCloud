package com.design.pattern.behavioral.simplefactory;

/**
 * 苹果手机类
 */
public class IPhone implements Phone {
    @Override
    public void getName() {
        System.out.println("苹果15ProMax 1TB 远峰蓝");
    }
}
