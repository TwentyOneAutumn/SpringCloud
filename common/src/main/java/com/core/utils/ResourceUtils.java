package com.core.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {

    /**
     * 复制resources下文件到指定路径
     * @param path resources下文件路径
     *             例如文件路径为: /resources/mapper/test.xml
     *             则传入参数path: mapper/test.xml
     * @param copyFilePath 要复制文件的目的地路径
     */
    public static void copyResourceFile(String path,String copyFilePath){
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = null;
        try {
            inputStream  = resource.getInputStream();
            FileUtils.copyToFile(inputStream,new File(copyFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Copy文件异常");
        }
    }
}
