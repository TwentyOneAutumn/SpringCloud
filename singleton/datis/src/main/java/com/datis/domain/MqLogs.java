package com.datis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_mqlogs")
public class MqLogs {

    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;

    @TableField("Type")
    private String type = "AtisInfo";

    @TableField("Msg")
    private String msg;

    @TableField("Source")
    private String Source;

    @TableField("ReadFlag")
    private Boolean readFlag = false;

    @TableField("CreateTime")
    private LocalDateTime createTime = LocalDateTime.now();

    @TableField("CreateUserID")
    private Integer createUserId = 0;

    @TableField("LastModifiedTime")
    private LocalDateTime lastModifiedTime = LocalDateTime.now();

    @TableField("LastModifiedUserID")
    private Integer lastModifiedUserId = 0;
}