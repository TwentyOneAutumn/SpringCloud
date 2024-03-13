package com.design.pattern.behavioral.factorymethod;

/**
 * Mac电脑类
 */
public class Mac implements Computer{

    @Override
    public void getType() {
        System.out.println("MacBook Pro M3Ultra 32G 2T 2023");
    }
}
