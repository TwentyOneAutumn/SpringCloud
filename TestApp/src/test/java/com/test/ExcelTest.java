package com.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.core.doMain.Build;
import com.core.doMain.TableInfo;
import com.core.utils.ExcelUtils;
import com.core.utils.StreamUtils;
import com.test.pojo.Contrast;
import com.test.pojo.DataInfo;
import com.test.pojo.Parameter;
import com.test.pojo.ParameterImport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class ExcelTest {

    @Test
    public void parameter(){
        List<ParameterImport> parameterImportList = ExcelUtils.importFile("/Users/thetwentyoneautumn/Downloads/管制员飞机性能一览表.xlsx", ParameterImport.class, "各机型性能参数表");
        List<String> valueList = new LinkedList<>();
        List<Parameter> parameterList = new ArrayList<>();
        parameterImportList.forEach(vo -> {
            String keyOne = vo.getKeyOne();
            if(keyOne.contains("机型代码") && CollUtil.isNotEmpty(valueList)){
                parameterList.add(new Parameter(
                        valueList.get(0),
                        valueList.get(4),
                        valueList.get(8),
                        valueList.get(12),
                        valueList.get(16),
                        valueList.get(20),
                        valueList.get(24),
                        valueList.get(28),
                        valueList.get(1),
                        valueList.get(5),
                        valueList.get(9),
                        valueList.get(13),
                        valueList.get(17),
                        valueList.get(21),
                        valueList.get(25),
                        valueList.get(29),
                        valueList.get(2),
                        valueList.get(6),
                        valueList.get(10),
                        valueList.get(14),
                        valueList.get(18),
                        valueList.get(22),
                        valueList.get(26),
                        valueList.get(30),
                        valueList.get(3),
                        valueList.get(7),
                        valueList.get(11),
                        valueList.get(15),
                        valueList.get(19),
                        valueList.get(23),
                        valueList.get(27)
                ));
                // 清空集合
                valueList.clear();
                valueList.add(vo.getValueOne());
                valueList.add(vo.getValueTwo());
                valueList.add(vo.getValueThree());
                valueList.add(vo.getValueFour());
            }
            else {
                valueList.add(vo.getValueOne());
                valueList.add(vo.getValueTwo());
                valueList.add(vo.getValueThree());
                if(!keyOne.contains("翼展")){
                    valueList.add(vo.getValueFour());
                }
            }
        });
        String jsonStr = JSONUtil.toJsonStr(parameterList);
        System.out.println(jsonStr);
    }

    @Test
    public void dataInfo(){
        List<DataInfo> DataInfoList = ExcelUtils.importFile("/Users/thetwentyoneautumn/Downloads/管制员飞机性能一览表.xlsx", DataInfo.class, "机型性能数据一览表");
        DataInfoList = StreamUtils.filterToList(DataInfoList,dataInfo -> !dataInfo.getCiaoCode().contains("机型代码"));
        String jsonStr = JSONUtil.toJsonStr(DataInfoList);
        System.out.println(jsonStr);
    }

    @Test
    public void contrast(){
        List<Contrast> ContrastList = ExcelUtils.importFile("/Users/thetwentyoneautumn/Downloads/管制员飞机性能一览表.xlsx", Contrast.class, "机型对照表");
        ContrastList = StreamUtils.filterToList(ContrastList,contrast -> !contrast.getCiaoCode().contains("机型代码"));
        String jsonStr = JSONUtil.toJsonStr(ContrastList);
        System.out.println(jsonStr);
    }
}
