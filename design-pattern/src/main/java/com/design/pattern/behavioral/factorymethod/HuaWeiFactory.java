package com.design.pattern.behavioral.factorymethod;

/**
 * 华为工厂类
 */
public class HuaWeiFactory implements ComputerFactory{

    @Override
    public HuaWei create() {
        return new HuaWei();
    }
}
