package com.generator.controller;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.*;
import com.baomidou.mybatisplus.generator.config.converts.TypeConverts;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.generator.domain.AjaxResult;
import com.generator.domain.Build;
import com.generator.domain.GeneratorCode;
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
import java.util.*;
import java.util.function.Function;

@Slf4j
@RestController
@RequestMapping
public class GeneratorController {

    @Autowired
    private DataSource dataSource;

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
                        // 开启Swagger
//                        .enableSwagger()
                        // 设置注释日期格式
                        .commentDate(generatorCode::getCommentDate)
                        // 指定文件输出目录
                        .outputDir(generatorCode.getOutputDir())
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
                        // 设置xml包名
                        // 指定输出路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml, generatorCode.getOutputDir() + File.separator + "resources" + File.separator + "mapper"))
                        .build()
                )
                .strategyConfig(builder -> {
                    Controller.Builder controllerBuilder = builder.controllerBuilder();
                    controllerBuilder.enableFileOverride();
                    Service.Builder serviceBuilder = builder.serviceBuilder();
                    serviceBuilder.enableFileOverride();
                    Entity.Builder entityBuilder = builder.entityBuilder();
                    entityBuilder.enableFileOverride()
                            .disableSerialVersionUID()
                            .logicDeleteColumnName("is_deleted")
                            .logicDeletePropertyName("isDeleted")
                            .idType(IdType.INPUT);
                    Mapper.Builder mapperBuilder = builder.mapperBuilder();
                    mapperBuilder.mapperAnnotation(org.apache.ibatis.annotations.Mapper.class).enableFileOverride();
                    builder.addInclude(generatorCode.getTableNameList()).build();
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
                            List<TableField> fields = table.getFields();
                            boolean isCreateTime = false;
                            boolean isUpdateTime = false;
                            for (TableField field : fields) {
                                if (field.isKeyFlag()) {
                                    String keyIdName = field.getPropertyName();
                                    String keyIdNameCapital = StringUtils.capitalize(keyIdName);
                                    objectMap.put("keyIdNameCapital", keyIdNameCapital);
                                }else {
                                    if("create_time".equals(field.getName())){
                                        isCreateTime = true;
                                    }else if("update_time".equals(field.getName())){
                                        isUpdateTime = true;
                                    }
                                }
                            }
                            objectMap.put("isExtendsTimeEntity", isCreateTime && isUpdateTime);
                            if(isCreateTime && isUpdateTime){
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
                        }})
                        .build())
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
                                    outputFile(new File(filePath + File.separator + "domain" + File.separator + packageName + File.separator + fileName), objectMap, file.getTemplatePath(), file.isFileOverride());
                                });
                            }
                        }
                )
                .execute();
        return Build.ajax(true, "代码生成完毕,路径为:" + generatorCode.getPath());
    }

    public String toClassLower(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
