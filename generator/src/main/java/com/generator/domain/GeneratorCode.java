package com.generator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorCode {

    /**
     * 表名集合,如果为空则默认为库中所有表生成代码
     */
    private List<String> includeTableNameList;

    /**
     * 排除表名集合,不会为被排除的表生成代码
     */
    private List<String> excludeTableNameList;

    /**
     * 作者
     */
    private String author = "Mystery Men";

    /**
     * 注释日期格式
     */
    private String commentDate = "yyyy年MM月dd日";

    /**
     * 输出路径
     */
    private String outputDir = Paths.get(System.getProperty("user.dir")).toAbsolutePath().toString();

    /**
     * 包名
     */
    private String parentPackage = "com";

    /**
     * 模块名
     */
    private String modulePackage = "demo";

    /**
     * 获取Java基础目录
     */
    public String getJavaOutputDir() {
        return outputDir + File.separator + modulePackage + File.separator + "main" + File.separator + "java";
    }

    /**
     * 获取项目基础目录
     */
    public String getBasicPath(){
        return outputDir + File.separator + modulePackage;
    }

    /**
     * 获取Xml文件基础目录
     */
    public String getXmlPath(){
        return outputDir + File.separator + modulePackage + File.separator + "main" + File.separator + "resources" + File.separator + "mapper";
    }
}
