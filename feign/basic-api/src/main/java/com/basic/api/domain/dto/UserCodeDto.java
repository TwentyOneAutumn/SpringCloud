package com.basic.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCodeDto implements Serializable {

    /**
     * 账号
     */
    private String userCode;
}
