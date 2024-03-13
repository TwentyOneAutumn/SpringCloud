package com.design.pattern.behavioral.abstractfactory;

/**
 * 苹果产品工厂
 */
public class AppleFactory implements AbstractProductFactory{

    @Override
    public IPhone createPhone() {
        return new IPhone();
    }

    @Override
    public MacBook createComputer() {
        return new MacBook();
    }
}
