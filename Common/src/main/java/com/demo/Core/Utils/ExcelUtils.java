package com.demo.Core.Utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.demo.Core.Interface.ExcleDataFormat;
import com.demo.Core.Interface.Export;
import com.demo.Core.Interface.Import;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelUtils {

    public static <T> List<T> importFile(MultipartFile file, Class<T> clazz){
        ExcelReader reader;
        try {
            reader = ExcelUtil.getReader(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("获取输入流异常");
        }
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
        ExcleDataFormat excleDataFormat = clazz.getAnnotation(ExcleDataFormat.class);
        if(BeanUtil.isNotEmpty(excleDataFormat)){
            DataFormat dataFormat = writer.getWorkbook().createDataFormat();
            short format = dataFormat.getFormat(excleDataFormat.format());
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
