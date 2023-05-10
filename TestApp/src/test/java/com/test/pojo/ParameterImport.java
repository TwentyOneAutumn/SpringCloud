package com.test.pojo;

import com.core.Interface.Import;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterImport implements Serializable {

    @Import("keyOne")
    private String keyOne;
    @Import("valueOne")
    private String valueOne;
    @Import("keyTwo")
    private String keyTwo;
    @Import("valueTwo")
    private String valueTwo;
    @Import("keyThree")
    private String keyThree;
    @Import("valueThree")
    private String valueThree;
    @Import("keyFour")
    private String keyFour;
    @Import("valueFour")
    private String valueFour;
}
