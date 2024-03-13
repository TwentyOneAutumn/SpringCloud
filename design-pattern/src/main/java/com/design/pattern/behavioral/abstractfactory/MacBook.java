package com.design.pattern.behavioral.abstractfactory;

/**
 * 苹果电脑类
 */
public class MacBook implements AbstractComputer{

    @Override
    public void getSystemType() {
         System.out.println("Apple MacBook Pro 32G 2T");
    }
}
