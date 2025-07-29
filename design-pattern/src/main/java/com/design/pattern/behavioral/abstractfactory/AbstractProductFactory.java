package com.design.pattern.behavioral.abstractfactory;

/**
 * 抽象产品工厂接口类
 */
public interface AbstractProductFactory {

    /**
     * 创建手机类型对象
     */
    <T extends AbstractPhone> T createPhone();

    /**
     * 创建电脑类型对象
     */
    <T extends AbstractComputer> T createComputer();
}
