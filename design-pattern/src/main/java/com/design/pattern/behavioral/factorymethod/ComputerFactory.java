package com.design.pattern.behavioral.factorymethod;

/**
 * 电脑工厂接口
 */
public interface ComputerFactory {

    <T extends Computer> T create();
}
