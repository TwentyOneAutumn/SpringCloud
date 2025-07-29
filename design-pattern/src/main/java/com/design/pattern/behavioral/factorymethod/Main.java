package com.design.pattern.behavioral.factorymethod;

/**
 * 测试类
 */
public class Main {

    public static void main(String[] args) {
        // 创建不同类型的工厂
        ComputerFactory macFactory = new MacFactory();
        ComputerFactory huaWeiFactory = new HuaWeiFactory();
        // 调用工厂创建对象
        Mac mac = macFactory.create();
        HuaWei huaWei = huaWeiFactory.create();
        // 查看品牌
        mac.getType();
        huaWei.getType();
    }
}
