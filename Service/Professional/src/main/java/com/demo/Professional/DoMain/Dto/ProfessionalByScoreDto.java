package com.demo.Professional.DoMain.Dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ProfessionalByScoreDto {
    @NotNull(message = "score参数不能为空")
    private int score;
    @NotNull(message = "sortFlg参数不能为空")
    private boolean sortFlg;
}
