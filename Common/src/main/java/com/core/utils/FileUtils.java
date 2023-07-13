package com.core.utils;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 */
public class FileUtils {

    /**
     * 拼接文件路径
     * @param paths 文件路径
     * @return 拼接后的文件路径
     */
    public static String joinFilePath(String... paths) {
        if(ArrayUtil.isEmpty(paths)){
            throw new IllegalArgumentException("The file path parameter is incorrect.");
        }
        return String.join(File.separator, paths);
    }

    /**
     * 文件夹不存在则创建
     * @param path 文件夹路径
     */
    public static void ofMkdir(String path){
        File folder = new File(path);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new RuntimeException("文件夹创建异常");
            }
        }
    }

    /**
     * 判断文件是否存在
     * @param path 文件路径
     * @return boolean:是否存在
     */
    public static boolean isExist(String path){
        return new File(path).exists();
    }

    /**
     * 写文件到指定路径
     * @param in 文件输入流
     * @param fullFilePath 文件路径
     */
    public static void write(InputStream in, String fullFilePath) throws IOException {
        if(BeanUtil.isEmpty(in)){
            throw new IllegalArgumentException("The InputStream cannot be empty.");
        }
        if(StrUtil.isEmpty(fullFilePath)){
            throw new IllegalArgumentException("The FilePath cannot be empty.");
        }
        File file = FileUtil.writeFromStream(in,fullFilePath);
        if(BeanUtil.isEmpty(file)){
            throw new IOException("写入文件异常");
        }
    }
}
