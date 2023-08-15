package com.core.utils;

import cn.hutool.core.bean.BeanUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * 图片工具类
 */
public class ImageUtils {

    /**
     * 将图片转换为Base64格式字符串
     * @param file 文件对象
     * @return Base64 encoded
     */
    public static String toBase64String(File file) {
        if(BeanUtil.isEmpty(file)){
            throw new RuntimeException("文件对象不能为空");
        }
        if(file.exists()){
            try {
                // 读取图片文件并将其转换为字节数组
                byte[] imageBytes = Files.readAllBytes(Paths.get(file.getPath()));

                // 将字节数组进行Base64编码
                return Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("转换Base64异常");
            }
        }else {
            throw new RuntimeException("文件不存在");
        }
    }

    /**
     * 将图片转换为Base64格式字符串
     * @param filePath 文件路径
     * @return  Base64 encoded
     */
    public static String toBase64String(String filePath) {
        File file = new File(filePath);
        if(file.exists()){
            try {
                // 读取图片文件并将其转换为字节数组
                byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));

                // 将字节数组进行Base64编码
                return Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("转换Base64异常");
            }
        }else {
            throw new RuntimeException("文件不存在");
        }
    }
}
