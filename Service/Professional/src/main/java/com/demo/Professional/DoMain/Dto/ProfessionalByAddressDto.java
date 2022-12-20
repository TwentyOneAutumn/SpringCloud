package com.demo.Professional.DoMain.Dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProfessionalByAddressDto {
    @NotBlank(message = "address参数不能为空")
    private String address;
    @NotNull(message = "sortFlg参数不能为空")
    private boolean sortFlg;
}