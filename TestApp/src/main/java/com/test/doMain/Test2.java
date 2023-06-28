package com.test.doMain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("test2")
public class Test2 {

    @TableId
    private String id;

    private String name;
}
