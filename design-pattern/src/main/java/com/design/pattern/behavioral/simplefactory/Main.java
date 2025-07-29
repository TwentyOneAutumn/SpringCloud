package com.design.pattern.behavioral.simplefactory;

/**
 * 测试类
 */
public class Main {

    public static void main(String[] args) {
        // 调用工厂创建方法,根据参数创建不同类型的手机
        Phone huawei = PhoneFactory.create("huawei");
        Phone xiaomi = PhoneFactory.create("xiaomi");
        Phone apple = PhoneFactory.create("apple");
        // 获取手机信息
        huawei.getName();
        xiaomi.getName();
        apple.getName();
    }
}
