//package com.atmb.generator.service.impl;
//
//import cn.hutool.core.util.StrUtil;
////import com.atmb.common.core.web.page.PageResult;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.atmb.generator.mapper.SysGeneratorMapper;
//import com.atmb.generator.service.SysGeneratorService;
//import com.atmb.generator.utils.GenUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.ZipOutputStream;
//
///**
// * @author yeyanrong
// */
//@Slf4j
//@Service
//public class SysGeneratorServiceImpl extends ServiceImpl implements SysGeneratorService {
//    @Autowired
//    private SysGeneratorMapper sysGeneratorMapper;
//
//    @Override
//    public Map<String, String> queryTable(String tableName,String dbName) {
//        return sysGeneratorMapper.queryTable(tableName,dbName);
//    }
//
//    @Override
//    public List<Map<String, String>> queryColumns(String tableName,String dbName) {
//        return sysGeneratorMapper.queryColumns(tableName,dbName);
//    }
//
//    @Override
//    public byte[] generatorCode(String[] tableNames,String dbName) {
//        if(StrUtil.isEmpty(dbName)){
//            dbName = "sipds";
//        }
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try (
//                ZipOutputStream zip = new ZipOutputStream(outputStream)
//        ) {
//            for (String tableName : tableNames) {
//                //查询表信息
//                Map<String, String> table = queryTable(tableName,dbName);
//                //查询列信息
//                List<Map<String, String>> columns = queryColumns(tableName,dbName);
//                //生成代码
//                GenUtils.generatorCode(table, columns, zip, dbName);
//            }
//        } catch (IOException e) {
//            log.error("generatorCode-error: ", e);
//        }
//        return outputStream.toByteArray();
//    }
//
//}
