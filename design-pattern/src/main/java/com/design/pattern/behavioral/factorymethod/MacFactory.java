package com.design.pattern.behavioral.factorymethod;

/**
 * Mac工厂类
 */
public class MacFactory implements ComputerFactory{

    @Override
    public Mac create() {
        return new Mac();
    }
}
