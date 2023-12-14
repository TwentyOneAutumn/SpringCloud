package com.test;

import com.test.service.ISysTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Autowired
    private ISysTestService sysTestService;

//    @Autowired
//    private SysTest2Mapper sysTest2Mapper;
//    @Autowired
//    private SysTestMapper sysTestMapper;



    @Test
    @Transactional
    public void test() throws Exception {
        double latitude = 22.103333; // 替换为你的纯数字纬度
        String formattedLatitude = convertLatitude(latitude);
        System.out.println("Formatted Latitude: " + formattedLatitude);
    }

    public String convertLatitude(double latitude) {
        String direction = (latitude >= 0) ? "N" : "S";
        latitude = Math.abs(latitude);

        int degrees = (int) latitude;
        latitude = (latitude - degrees) * 60;
        int minutes = (int) latitude;
        latitude = (latitude - minutes) * 60;
        int seconds = (int) latitude; // 取整数部分，不显示小数秒

        return String.format("%s%d°%02d`%02d\"", direction, degrees, minutes, seconds);

    }

//    @Test
//    @Transactional
//    public void test() throws Exception {
//        List<SysTest2> sysTest2s = sysTest2Mapper.selectList(null);
//        List<SysTest> sysTests = sysTestMapper.selectList(null);
//        System.out.println(sysTest2s);
//        System.out.println(sysTests);
//    }


}
