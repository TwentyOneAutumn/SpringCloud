package com.test.pojo;

import com.core.Interface.Import;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contrast {


    /**
     * 机型代码
     */
    @Import("ciaoCode")
    private String ciaoCode;


    /**
     * 机型名称
     */
    @Import("aircraftName")
    private String aircraftName;
}
