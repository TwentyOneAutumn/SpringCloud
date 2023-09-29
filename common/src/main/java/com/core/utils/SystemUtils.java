package com.core.utils;

import java.nio.file.Paths;

/**
 * 系统工具类
 */
public class SystemUtils {

    /**
     * 获取当前项目绝对路径
     * @return 项目绝对路径
     */
    public static String projectPath(){
        return Paths.get(System.getProperty("user.dir")).toAbsolutePath().toString();
    }
}
