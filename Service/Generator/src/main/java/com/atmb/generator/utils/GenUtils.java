package com.atmb.generator.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.atmb.common.core.utils.DateUtils;
import com.atmb.generator.model.ColumnEntity;
import com.atmb.generator.model.TableEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器工具类
 *
 * @author zlt
 */
@Slf4j
public class GenUtils {
    private GenUtils() {
        throw new IllegalStateException("Utility class");
    }

    private final static String FILE_NAME_DOMAIN = "Domain.java.vm";
    private final static String FILE_NAME_MAPPER = "Mapper.java.vm";
    private final static String FILE_NAME_MAPPERXML = "Mapper.xml.vm";
    private final static String FILE_NAME_SERVICE = "Service.java.vm";
    private final static String FILE_NAME_SERVICEIMPL = "ServiceImpl.java.vm";
    private final static String FILE_NAME_CONTROLLER = "Controller.java.vm";
    private final static String FILE_NAME_DTO_LIST = "DtoList.java.vm";
    private final static String FILE_NAME_DTO_DETAIL = "DtoDetail.java.vm";
    private final static String FILE_NAME_DTO_ADD = "DtoAdd.java.vm";
    private final static String FILE_NAME_DTO_EDIT = "DtoEdit.java.vm";
    private final static String FILE_NAME_DTO_DELETE = "DtoDelete.java.vm";
    private final static String FILE_NAME_VO_LIST = "VoList.java.vm";
    private final static String FILE_NAME_VO_DETAIL = "VoDetail.java.vm";


    private final static String TEMPLATE_PATH = "template/";
    private final static String PACKAGE = "package";
    private final static String MODULE_NAME = "moduleName";
    private final static String PACKAGE_NAME = "packageName";


    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(TEMPLATE_PATH + FILE_NAME_DOMAIN);
        templates.add(TEMPLATE_PATH + FILE_NAME_CONTROLLER);
        templates.add(TEMPLATE_PATH + FILE_NAME_MAPPER);
        templates.add(TEMPLATE_PATH + FILE_NAME_MAPPERXML);
        templates.add(TEMPLATE_PATH + FILE_NAME_SERVICE);
        templates.add(TEMPLATE_PATH + FILE_NAME_SERVICEIMPL);
        templates.add(TEMPLATE_PATH + FILE_NAME_DTO_LIST);
        templates.add(TEMPLATE_PATH + FILE_NAME_DTO_DETAIL);
        templates.add(TEMPLATE_PATH + FILE_NAME_DTO_ADD);
        templates.add(TEMPLATE_PATH + FILE_NAME_DTO_EDIT);
        templates.add(TEMPLATE_PATH + FILE_NAME_DTO_DELETE);
        templates.add(TEMPLATE_PATH + FILE_NAME_VO_LIST);
        templates.add(TEMPLATE_PATH + FILE_NAME_VO_DETAIL);

        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns,
                                     ZipOutputStream zip,
                                     String dbName) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("TABLENAME"));
        tableEntity.setComments(table.get("TABLECOMMENT"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("COLUMNNAME"));
            columnEntity.setDataType(column.get("DATATYPE"));
            columnEntity.setComments(column.get("COLUMNCOMMENT"));
            columnEntity.setExtra(column.get("EXTRA"));
            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());

            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
//            String attrType = config.getString(columnEntity.getDataType(), "LocalDateTime");
            // 转为为java类型 转为小写去除空格
            String attrType = config.getString(columnEntity.getDataType().trim().toLowerCase(), "LocalDateTime");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        String mainPath = config.getString("mainPath");
        mainPath = StringUtils.isBlank(mainPath) ? "io.renren" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        // 暂时没有用到
        map.put("mainPath", mainPath);

        map.put(PACKAGE, config.getString(PACKAGE));
        map.put(MODULE_NAME, config.getString(MODULE_NAME));
        map.put(PACKAGE_NAME, config.getString(PACKAGE_NAME));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtil.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            try (
                    StringWriter sw = new StringWriter()
            ) {
                Template tpl = Velocity.getTemplate(template, "UTF-8");
                tpl.merge(context, sw);

                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), config.getString(PACKAGE), config.getString(MODULE_NAME),config.getString(PACKAGE_NAME))));
                IOUtils.write(sw.toString(), zip, StandardCharsets.UTF_8);
                zip.closeEntry();
            } catch (IOException e) {
                log.error("generatorCode-error", e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.substring(tablePrefix.length());
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName,String classPackageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains(FILE_NAME_DOMAIN)) {
            return packagePath + "domain" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "")  + className + ".java";
        }

        if (template.contains(FILE_NAME_DTO_LIST)) {
            return packagePath + "domain" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + "dto" + File.separator  + className + "ListDto" + ".java";
        }

        if (template.contains(FILE_NAME_DTO_DETAIL)) {
            return packagePath + "domain"+ File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + "dto" + File.separator  + className + "DetailDto" + ".java";
        }

        if (template.contains(FILE_NAME_DTO_ADD)) {
            return packagePath + "domain"+ File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + "dto" + File.separator + className + "AddDto" + ".java";
        }

        if (template.contains(FILE_NAME_DTO_EDIT)) {
            return packagePath + "domain"+ File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") +"dto" + File.separator  + className + "EditDto" + ".java";
        }

        if (template.contains(FILE_NAME_DTO_DELETE)) {
            return packagePath + "domain"+ File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") +"dto" + File.separator  + className + "DeleteDto" + ".java";
        }

        if (template.contains(FILE_NAME_VO_LIST)) {
            return packagePath + "domain" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + "vo" + File.separator  + className + "ListVo" + ".java";
        }

        if (template.contains(FILE_NAME_VO_DETAIL)) {
            return packagePath + "domain" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + "vo" + File.separator  + className + "DetailVo" + ".java";
        }

        if (template.contains(FILE_NAME_MAPPER)) {
            return packagePath + "mapper" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + className + "Mapper.java";
        }

        if (template.contains(FILE_NAME_SERVICE)) {
            return packagePath + "service" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + "I" + className + "Service.java";
        }

        if (template.contains(FILE_NAME_SERVICEIMPL)) {
            return packagePath + "service" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(FILE_NAME_CONTROLLER)) {
            return packagePath + "controller" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + className + "Controller.java";
        }

        if (template.contains(FILE_NAME_MAPPERXML)) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + (StrUtil.isNotEmpty(classPackageName) ? (classPackageName + File.separator) : "") + className + "Mapper.xml";
        }

        return null;
    }
}
