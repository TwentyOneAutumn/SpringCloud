package com.design.pattern.behavioral.simplefactory;

/**
 * 手机工厂类
 */
public class PhoneFactory {

    /**
     * 创建手机对象
     * @param type 手机类型
     * @return 手机对象
     */
    public static Phone create(String type){
        Phone phone = null;
        switch (type){
            case "huawei":{
                phone = new HuaWei();
                break;
            }
            case "xiaomi":{
                phone = new XiaoMi();
                break;
            }
            case "apple":{
                phone = new IPhone();
                break;
            }
        }
        return phone;
    }
}
