package com.design.pattern.behavioral.abstractfactory;

/**
 * 测试类
 */
public class Main {

    public static void main(String[] args) {
        // 创建不同的产品工厂对象
        HuaWeiFactory huaWeiFactory = new HuaWeiFactory();
        AppleFactory appleFactory = new AppleFactory();
        // 调用工厂创建产品对象
        Mate60Pro mate60Pro = huaWeiFactory.createPhone();
        MateBook mateBook = huaWeiFactory.createComputer();
        IPhone iPhone = appleFactory.createPhone();
        MacBook macBook = appleFactory.createComputer();
        // 调用产品方法获取信息
        mate60Pro.getBrand();
        mateBook.getSystemType();
        iPhone.getBrand();
        macBook.getSystemType();
    }
}
