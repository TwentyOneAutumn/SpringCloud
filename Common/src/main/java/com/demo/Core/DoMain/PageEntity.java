package com.demo.Core.DoMain;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分页实体类
 */
@Data
public class PageEntity {


    /**
     * 页数
     */
    @NotNull(message = "pageNum参数不能为空")
    private Integer pageNum;


    /**
     * 页码
     */
    @NotNull(message = "pageSize参数不能为空")
    private Integer pageSize;
}