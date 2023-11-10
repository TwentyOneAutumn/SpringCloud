package com.generator.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.*;
import com.baomidou.mybatisplus.generator.config.converts.TypeConverts;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.generator.domain.AjaxResult;
import com.generator.domain.Build;
import com.generator.domain.GeneratorCode;
import com.generator.mapper.TableMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping
public class GeneratorController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TableMapper tableMapper;

    @PostMapping("/code")
    public AjaxResult generatingCode(@Valid @RequestBody GeneratorCode generatorCode) {
        FastAutoGenerator.create(new DataSourceConfig.Builder(dataSource))
                .dataSourceConfig(builder -> builder
                        .dbQuery(new MySqlQuery())
                        .typeConvert(TypeConverts.getTypeConvert(DbType.MYSQL))
                )
                .globalConfig(builder -> builder
                        // 设置作者
                        .author(generatorCode.getAuthor())
                        // 设置注释日期格式
                        .commentDate(() -> new SimpleDateFormat(generatorCode.getCommentDate()).format(new Date()))
                        // 指定文件输出目录
                        .outputDir(generatorCode.getJavaOutputDir())
                        // 禁止打开输出目录
                        .disableOpenDir()
                        .build()
                )
                .packageConfig(builder -> builder
                        // 设置父包名
                        .parent(generatorCode.getParentPackage())
                        // 设置模块名
                        .moduleName(generatorCode.getModulePackage())
                        // 设置实体类包名
                        .entity("domain")
                        // 设置controller包名
                        .controller("controller")
                        // 设置service包名
                        .service("service")
                        // 设置serviceImpl包名
                        .serviceImpl("service.impl")
                        // 设置mapper包名
                        .mapper("mapper")
                        // 指定输出路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml, generatorCode.getXmlPath()))
                        .build()
                )
                .strategyConfig(builder -> {
                    // Controller配置
                    Controller.Builder controllerBuilder = builder.controllerBuilder();
                    // 开启生成@RestController控制器
                    controllerBuilder.enableRestStyle();
                    // 覆盖已有文件
                    controllerBuilder.enableFileOverride();

                    // Service配置
                    Service.Builder serviceBuilder = builder.serviceBuilder();
                    // 覆盖已有文件
                    serviceBuilder.enableFileOverride();

                    // Entity配置
                    Entity.Builder entityBuilder = builder.entityBuilder();
                    // 覆盖已有文件
                    entityBuilder.enableFileOverride();
                    // 禁用生成serialVersionUID
                    entityBuilder.disableSerialVersionUID();
                    // 逻辑删除数据库字段名称
                    entityBuilder.logicDeleteColumnName("is_deleted");
                    // 逻辑删除实体属性名称
                    entityBuilder.logicDeletePropertyName("isDeleted");
                    // 指定生成的主键的ID类型
                    entityBuilder.idType(IdType.INPUT);
                    // 开启链式模型
                    entityBuilder.enableChainModel();

                    // Mapper配置
                    Mapper.Builder mapperBuilder = builder.mapperBuilder();
                    // 标记 Mapper 注解
                    mapperBuilder.mapperAnnotation(org.apache.ibatis.annotations.Mapper.class);
                    // 覆盖已有文件
                    mapperBuilder.enableFileOverride();


                    List<String> includeTableNameList = generatorCode.getIncludeTableNameList();
                    List<String> excludeTableNameList = generatorCode.getExcludeTableNameList();
                    if(CollUtil.isEmpty(includeTableNameList)){
                        // 获取数据库名称
                        String databaseName;
                        try {
                            databaseName = dataSource.getConnection().getMetaData().getDatabaseProductName();
                        } catch (Exception e) {
                            throw new RuntimeException("获取数据库名称异常");
                        }
                        // 获取库中所有表名
                        includeTableNameList = tableMapper.getTableNameAll(databaseName);
                    }

                    if(CollUtil.isNotEmpty(includeTableNameList)){
                        // 要生成代码的表名集合
                        builder.addExclude();
                    }
                    if(CollUtil.isNotEmpty(excludeTableNameList)){
                        // 排除在外的表名集合
                        builder.addExclude();
                    }
                })
                .injectionConfig(builder -> builder
                        .beforeOutputFile(((tableInfo, objectMap) -> {
                            TableInfo table = (TableInfo) objectMap.get("table");
                            objectMap.put("controllerMappingHyphen", table.getEntityPath());
                            objectMap.put("entityNameLower", toClassLower(table.getEntityName()));
                            objectMap.put("mapperNameLower", toClassLower(table.getMapperName()));
                            objectMap.put("serviceNameLower", toClassLower(table.getServiceName().substring(1)));
                            objectMap.put("serviceImplNameLower", toClassLower(table.getServiceImplName()));
                            objectMap.put("controllerNameLower", toClassLower(table.getControllerName()));
                            String entityPackage = generatorCode.getParentPackage() + "." + generatorCode.getModulePackage() + ".entity";
                            objectMap.put("AjaxResultClassName", "AjaxResult");
                            objectMap.put("BuildClassName", "Build");
                            objectMap.put("HttpStatusClassName", "HttpStatus");
                            objectMap.put("PageEntityClassName", "PageEntity");
                            objectMap.put("RowClassName", "Row");
                            objectMap.put("TableInfoClassName", "TableInfo");
                            objectMap.put("TimeEntityClassName", "TimeEntity");

                            objectMap.put("EntityPackage", entityPackage);

                            List<TableField> fields = table.getFields();
                            boolean isCreateTime = false;
                            boolean isUpdateTime = false;
                            for (TableField field : fields) {
                                if (field.isKeyFlag()) {
                                    String keyIdName = field.getPropertyName();
                                    String keyIdNameCapital = StringUtils.capitalize(keyIdName);
                                    objectMap.put("keyIdNameCapital", keyIdNameCapital);
                                } else {
                                    if ("create_time".equals(field.getName())) {
                                        isCreateTime = true;
                                    } else if ("update_time".equals(field.getName())) {
                                        isUpdateTime = true;
                                    }
                                }
                            }
                            objectMap.put("isExtendsTimeEntity", isCreateTime && isUpdateTime);
                            if (isCreateTime && isUpdateTime) {
                                objectMap.put("timeEntityName", "TimeEntity");
                                objectMap.put("timeEntityPackage", "com.core.doMain.TimeEntity");
                            }
                        }))
                        .customFile(new HashMap<String, String>() {{
                                    put("ListDto.java", "/templates/dtoList.java.vm");
                                    put("DetailDto.java", "/templates/dtoDetail.java.vm");
                                    put("AddDto.java", "/templates/dtoAdd.java.vm");
                                    put("EditDto.java", "/templates/dtoEdit.java.vm");
                                    put("DeleteDto.java", "/templates/dtoDelete.java.vm");
                                    put("ListVo.java", "/templates/voList.java.vm");
                                    put("DetailVo.java", "/templates/voDetail.java.vm");

                                    put("AjaxResult.java", "/templates/AjaxResult.java.vm");
                                    put("Build.java", "/templates/Build.java.vm");
                                    put("HttpStatus.java", "/templates/HttpStatus.java.vm");
                                    put("PageEntity.java", "/templates/PageEntity.java.vm");
                                    put("Row.java", "/templates/Row.java.vm");
                                    put("TableInfo.java", "/templates/TableInfo.java.vm");
                                    put("TimeEntity.java", "/templates/TimeEntity.java.vm");
                                }}.entrySet().stream().map(e -> new CustomFile.Builder().fileName(e.getKey()).templatePath(e.getValue()).enableFileOverride().build()).collect(Collectors.toList())
                        )
                        .build()
                )
                .templateEngine(
                        new VelocityTemplateEngine() {
                            @Override
                            protected void outputCustomFile(List<CustomFile> customFiles, TableInfo tableInfo, Map<String, Object> objectMap) {
                                String entityName = tableInfo.getEntityName();
                                String parentPath = getPathInfo(OutputFile.parent);
                                customFiles.forEach(file -> {
                                    String filePath = StringUtils.isNotBlank(file.getFilePath()) ? file.getFilePath() : parentPath;
                                    if (StringUtils.isNotBlank(file.getPackageName())) {
                                        filePath = filePath + File.separator + file.getPackageName().replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
                                    }
                                    Function<TableInfo, String> formatNameFunction = file.getFormatNameFunction();
                                    String name = file.getFileName();
                                    String fileName = (null != formatNameFunction ? formatNameFunction.apply(tableInfo) : entityName) + file.getFileName();
                                    String packageName = "";
                                    if (name.contains("Vo") && !name.contains("Dto")) {
                                        packageName = "vo";
                                    }
                                    if (name.contains("Dto") && !name.contains("Vo")) {
                                        packageName = "dto";
                                    }
                                    if (name.equals("AjaxResult.java") || name.equals("Build.java") || name.equals("HttpStatus.java") || name.equals("PageEntity.java") || name.equals("Row.java") || name.equals("TableInfo.java") || name.equals("TimeEntity.java")) {
                                        outputFile(new File(filePath + File.separator + "entity" + File.separator + file.getFileName()), objectMap, file.getTemplatePath(), file.isFileOverride());
                                    } else {
                                        outputFile(new File(filePath + File.separator + "domain" + File.separator + packageName + File.separator + fileName), objectMap, file.getTemplatePath(), file.isFileOverride());
                                    }
                                });
                            }
                        }
                )
                .execute();
        return Build.ajax(true, "代码生成完毕,路径为:" + generatorCode.getBasicPath());
    }

    public String toClassLower(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
