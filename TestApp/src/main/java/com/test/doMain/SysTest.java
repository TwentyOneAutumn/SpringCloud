package com.test.doMain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.doMain.TimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_test")
@EqualsAndHashCode(callSuper = true)
public class SysTest extends TimeEntity {

    @TableId(type = IdType.INPUT)
    private String id;

    private String code;

    private Integer age;
}
