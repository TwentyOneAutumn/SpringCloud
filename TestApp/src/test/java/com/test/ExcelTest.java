package com.test;

import com.core.utils.ExcelUtils;
import com.test.pojo.ParameterImport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ExcelTest {

    @Test
    public void importExcel(){

        List<ParameterImport> parameterImportList = ExcelUtils.importFile("/Users/thetwentyoneautumn/Downloads/管制员飞机性能一览表.xlsx", ParameterImport.class, "各机型性能参数表");
        parameterImportList.forEach(vo -> {
            String keyOne = vo.getKeyOne();
            if(keyOne.indexOf("12321") > 0){
            
            }
        });
        System.out.println(parameterImportList);
    }
}
