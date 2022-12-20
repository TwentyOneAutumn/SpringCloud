package com.demo.School.DoMain.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SchoolListVo {
    private String schoolId;
    private String schoolName;
    private String address;
    private int orderId;
}
