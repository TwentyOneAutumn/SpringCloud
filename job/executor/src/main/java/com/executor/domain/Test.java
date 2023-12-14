package com.executor.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.core.doMain.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_test")
@KeySequence("KeyGenerator")
@EqualsAndHashCode(callSuper = true)
public class Test extends TimeEntity {

    @TableId(type = IdType.INPUT)
    private String id;

    private String code;

    private Integer age;
}
