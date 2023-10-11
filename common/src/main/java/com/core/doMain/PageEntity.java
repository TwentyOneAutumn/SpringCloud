package com.core.doMain;

import com.github.pagehelper.PageHelper;
import lombok.Data;
import javax.validation.constraints.NotNull;
import com.github.pagehelper.Page;

/**
 * 分页实体类
 */
@Data
public class PageEntity {


    /**
     * 页数
     */
    @NotNull(message = "pageNum参数不能为空")
    private Integer pageNum = 1;


    /**
     * 页码
     */
    @NotNull(message = "pageSize参数不能为空")
    private Integer pageSize = 10;


    /**
     * 构建分页信息
     * @param page PageEntity子类
     * @return Page对象
     */
    public static <T extends PageEntity> Page<Object> build(T page){
        return PageHelper.startPage(page.getPageNum(), page.getPageSize());
    }
}
