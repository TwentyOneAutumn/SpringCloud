package com.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    /**
     * 将文本内容追加到文件
     * 如果文件不存在会自动创建文件
     * @param file 文件对象
     * @param data 文本内容
     * @param append 是否追加
     * @throws IOException IO异常
     */
    public static void write(File file, String data, boolean append) throws IOException {
        if(BeanUtil.isEmpty(file)){
            throw new IllegalArgumentException("The file cannot be empty.");
        }
        if(StrUtil.isEmpty(data)){
            throw new IllegalArgumentException("The data cannot be empty.");
        }
        FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), append);
        fileWriter.write(data);
        fileWriter.close();
    }

    /**
     * 将文本内容追加到文件
     * 如果文件不存在会自动创建文件
     * @param filePath 文件对象路径
     * @param data 文本内容
     * @param append 是否追加
     * @throws IOException IO异常
     */
    public static void write(String filePath, String data, boolean append) throws IOException {
        if(StrUtil.isEmpty(filePath)){
            throw new IllegalArgumentException("The filePath cannot be empty.");
        }
        if(StrUtil.isEmpty(data)){
            throw new IllegalArgumentException("The data cannot be empty.");
        }
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.write(data);
        fileWriter.close();
    }

    /**
     * 将输入流写入到输出流中
     * 流在使用后会自动关闭
     * @param inputStream 输入流
     * @param outputStream 输出流
     * @throws IOException 异常
     */
    public static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        // 健壮性判断
        if(BeanUtil.isNotEmpty(inputStream)){
            throw new IOException("InputStream not null.");
        }else if(BeanUtil.isNotEmpty(outputStream)){
            throw new IOException("OutputStream not null.");
        }

        // 初始化变量
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        // 循环写入
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // 刷新输出流
        outputStream.flush();

        // 关闭输入输出流
        inputStream.close();
        outputStream.close();
    }


    /**
     * 将输入流写入到输出流中
     * 流在使用后会自动关闭
     * @param file 文件对象
     * @param outputStream 输出流
     * @throws IOException 异常
     */
    public static void write(File file, OutputStream outputStream) throws IOException {
        // 判空
        if(BeanUtil.isEmpty(file)){
            throw new IOException("File not null.");
        }else if(!file.exists()) {
            throw new IOException("File not exist.");
        }

        // 获取InputStream
        InputStream inputStream = Files.newInputStream(file.toPath());

        // 写入
        write(inputStream,outputStream);
    }

    public static void write(MultipartFile file, OutputStream outputStream) throws IOException {
        // 判空
        if(BeanUtil.isEmpty(file)){
            throw new IOException("File not null.");
        }else if(file.isEmpty()) {
            throw new IOException("File not exist.");
        }

        // 获取InputStream
        InputStream inputStream = file.getInputStream();

        // 写入
        write(inputStream,outputStream);
    }

    public static void write(MultipartFile file, String path) throws IOException {
        // 判空
        if(StrUtil.isEmpty(path)){
            throw new IOException("File path not null.");
        }

        // 构建OutputStream
        OutputStream outputStream = Files.newOutputStream(Paths.get(path));

        // 写入
        write(file,outputStream);
    }

    public static void write(File file, String path) throws IOException {
        // 判空
        if(StrUtil.isEmpty(path)){
            throw new IOException("File path not null.");
        }

        // 构建OutputStream
        OutputStream outputStream = Files.newOutputStream(Paths.get(path));

        // 写入
        write(file,outputStream);
    }

    public static void write(String filePath, String path) throws IOException {
        // 判空
        if(StrUtil.isEmpty(filePath) || StrUtil.isEmpty(path)){
            throw new IOException("File path not null.");
        }
        File file = new File(filePath);
        if(!file.exists()){
            throw new IOException("File not exists.");
        }

        // 构建OutputStream
        OutputStream outputStream = Files.newOutputStream(Paths.get(path));

        // 写入
        write(file,outputStream);
    }

    public static void write(String path, OutputStream outputStream) throws IOException {
        // 判空
        if(StrUtil.isEmpty(path)){
            throw new IOException("File path not null.");
        }

        // 写入
        write(new File(path),outputStream);
    }
}
