package com.demo.user.DoMain.Dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class AdminUserListDto {
    @NotNull(message = "isDeleted参数不能为空")
    private Boolean isDeleted;
}
