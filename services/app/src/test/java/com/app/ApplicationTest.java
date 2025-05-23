package com.app;

import cn.hutool.core.collection.CollUtil;
import com.app.config.AesUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ApplicationTest {

    @Test
    public void test2() throws Exception {
        String inputPath = "/Users/maplej/Downloads/headimg";
        String outPath = "/Users/maplej/Downloads/headimgAES";
        File directory = new File(inputPath);
        File[] files = directory.listFiles();
        for (File file : files) {
            // 获取文件名
            String fileName = file.getName().toLowerCase();
            if(fileName.contains(".jpg")){
                // 截取文件前缀
                String filePrefix = fileName.substring(0, fileName.indexOf("_"));
                String fileSuffix = fileName.substring(fileName.indexOf("."));
                String encryptionFilePrefix = AesUtil.encrypt(filePrefix);
                // 进行替换
                String encryptionFileName = (encryptionFilePrefix + fileSuffix).toLowerCase();
                Files.copy(Files.newInputStream(file.toPath()), Paths.get(outPath + File.separator + encryptionFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    @Test
    public void test3() throws Exception {
        String inputPath = "/Users/maplej/Downloads/headimgAES";
        File directory = new File(inputPath);
        File[] files = directory.listFiles();
        Map<String, String> collect = Arrays.stream(files).filter(file -> file.getName().contains(".jpg")).collect(
                Collectors.toMap(
                        file -> {
                            String fileName = file.getName();
                            // 截取文件前缀
                            String filePrefix = fileName.substring(0, fileName.indexOf("."));
                            return AesUtil.decrypt(filePrefix);
                        },
                        file -> {
                            return "http://88.38.4.236:7031/" + file.getName();
                        }
                )
        );
        collect.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
        int size = collect.size();
        System.out.println(size);
    }

    @Test
    public void test4() throws Exception {
        String inputPath = "/Users/maplej/Downloads/headimg";
        String outPath = "/Users/maplej/Downloads/headimg2";
        File directory = new File(inputPath);
        File[] files = directory.listFiles();
        List<File> fileList = Arrays.stream(files).filter(file -> file.getName().contains("x")).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(fileList)){
            for (File file : fileList) {
                String replaceFileName = file.getName().replace(".JPG", ".jpg");
                Files.copy(Files.newInputStream(file.toPath()), Paths.get(outPath + File.separator + replaceFileName), StandardCopyOption.REPLACE_EXISTING);
                file.delete();
            }
        }
    }
}
