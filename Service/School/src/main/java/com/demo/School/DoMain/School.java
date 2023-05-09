package com.demo.School.DoMain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.Core.DoMain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_school")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class School extends BaseEntity {
    @TableId(type = IdType.INPUT)
    private String schoolId;
    private String schoolCode;
    private String schoolName;
    private String address;
    private int orderId;
}
