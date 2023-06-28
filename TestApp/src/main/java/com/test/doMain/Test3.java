package com.test.doMain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("test3")
public class Test3 {

    @TableId
    private String id;

    private String name;
}
