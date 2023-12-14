package com.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.core.interfaces.ExcelDataFormat;
import com.core.interfaces.Export;
import com.core.interfaces.Import;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel工具类
 */
public class ExcelUtils {


    /**
     * 导入Excel
     * @param file Excel文件对象
     * @param clazz 要转换的对象Class
     * @param <T> 泛型
     * @return 转换后的对象集合
     */
    public static <T> List<T> importFile(MultipartFile file, Class<T> clazz){
        try {
            return importFile(file.getInputStream(),clazz,null);
        } catch (IOException e) {
            throw new RuntimeException("获取文件输入流异常");
        }
    }


    /**
     * 导入Excel
     * @param filePath Excel文件路径
     * @param clazz 要转换的对象Class
     * @param <T> 泛型
     * @return 转换后的对象集合
     */
    public static <T> List<T> importFile(String filePath, Class<T> clazz){
        return importFile(filePath,clazz,null);
    }


    /**
     * 导入Excel
     * @param filePath Excel文件路径
     * @param clazz 要转换的对象Class
     * @param sheetName sheet页名称
     * @param <T> 泛型
     * @return 转换后的对象集合
     */
    public static <T> List<T> importFile(String filePath, Class<T> clazz, String sheetName){
        try {
            File file = new File(filePath);
            return importFile(new FileInputStream(file),clazz,sheetName);
        } catch (IOException e) {
            throw new RuntimeException("获取文件输入流异常");
        }
    }


    /**
     * 导入Excel
     * @param bookStream Excel文件对象输入流对象
     * @param clazz 要转换的对象Class
     * @param sheetName sheet页名称
     * @param <T> 泛型
     * @return 转换后的对象集合
     */
    public static <T> List<T> importFile(InputStream bookStream, Class<T> clazz, String sheetName){
        ExcelReader reader;
        reader = StrUtil.isNotEmpty(sheetName) ? ExcelUtil.getReader(bookStream,sheetName) : ExcelUtil.getReader(bookStream);
        HashMap<String, String> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            // 获取字段的 @Import 注解
            Import anImport = field.getAnnotation(Import.class);
            // 有 @Import 注解
            if (BeanUtil.isNotEmpty(anImport)) {
                map.put(anImport.value(),field.getName());
            }
        });
        map.forEach(reader::addHeaderAlias);
        // 读取文件数据为Bean
        List<T> list = reader.readAll(clazz);
        list.forEach(obj -> {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            Arrays.stream(declaredFields).forEach(field -> {
                field.setAccessible(true);
                // 获取字段的 @Import 注解
                Import anImport = field.getAnnotation(Import.class);
                // 有 @Import 注解
                if (BeanUtil.isNotEmpty(anImport)) {
                    if(!anImport.isNull()){
                        try {
                            if(BeanUtil.isEmpty(field.get(obj))){
                                throw new RuntimeException(anImport.errorMsg());
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("获取字段值异常");
                        }
                    }
                }
            });
        });
        return list;
    }


    /**
     * 导出Excel
     * @param response 响应对象
     * @param fileName Excel文件对象
     * @param sheetName sheet页名称
     * @param list 要导入的数据集合
     * @param clazz 要导入的数据对象Class
     * @param <E> 泛型
     */
    public static <E> void export(HttpServletResponse response,String fileName, String sheetName, List<E> list, Class<E> clazz){
        // 创建writer
        ExcelWriter writer = new ExcelWriter(true,sheetName);
        // 设置响应信息
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        HashMap<String, String> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            // 获取字段的 @Export 注解
            Export export = field.getAnnotation(Export.class);
            // 有 @Export 注解
            if (BeanUtil.isNotEmpty(export)) {
                map.put(field.getName(),export.value());
            }
        });
        map.forEach(writer::addHeaderAlias);

        // 设置时间格式
        ExcelDataFormat excelDataFormat = clazz.getAnnotation(ExcelDataFormat.class);
        if(BeanUtil.isNotEmpty(excelDataFormat)){
            DataFormat dataFormat = writer.getWorkbook().createDataFormat();
            short format = dataFormat.getFormat(excelDataFormat.format());
            StyleSet styleSet = writer.getStyleSet();
            styleSet.getCellStyleForDate()
                    .setDataFormat(format);
        }
        // 写入数据到流
        writer.write(list, true);
        // 获取out流
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            // 写入数据到响应
            writer.flush(out,true);
        } catch (IOException e) {
            throw new RuntimeException("获取输出流异常");
        }
        finally {
            // 关闭流
            writer.close();
            IoUtil.close(out);
        }
    }
}
