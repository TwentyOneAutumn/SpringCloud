package com.demo.Professional.DoMain;

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
@TableName("sys_professional")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class Professional extends BaseEntity {
    @TableId(type = IdType.INPUT)
    private String professionalId;
    private String schoolId;
    private String professionalCode;
    private String professionalName;
    private String lowestMark;
    private String orderId;
}
