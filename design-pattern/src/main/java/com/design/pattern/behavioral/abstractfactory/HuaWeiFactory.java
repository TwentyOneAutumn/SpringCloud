package com.design.pattern.behavioral.abstractfactory;

/**
 * 华为产品工厂
 */
public class HuaWeiFactory implements AbstractProductFactory{

    @Override
    public Mate60Pro createPhone() {
        return new Mate60Pro();
    }

    @Override
    public MateBook createComputer() {
        return new MateBook();
    }
}
