package com.core.doMain.file;

import com.baomidou.mybatisplus.annotation.*;
import com.core.doMain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("file_resource")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class FileResource extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String filePostfix;

    /**
     * 文件所在路径
     */
    private String filePath;

    /**
     * 所属模块
     */
    private String moduleName;

    /**
     * 上传人员ID
     */
    private String uploadUserId;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDeleted;
}
