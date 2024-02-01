package com.test;

import com.core.doMain.MapEntry;
import com.core.utils.TimeUtils;
import com.test.service.ISysTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Autowired
    private ISysTestService sysTestService;

    @Test
    @Transactional
    public void test() throws Exception {

    }

    public static void main(String[] args) {
        List<MapEntry<LocalDateTime,LocalDateTime>> list = new ArrayList<>();
        list.add(MapEntry.create(LocalDateTime.of(2000,1,1,1,1,1),LocalDateTime.of(2000,3,1,1,1,1)));
        list.add(MapEntry.create(LocalDateTime.of(2000,2,1,1,1,1),LocalDateTime.of(2000,4,1,1,1,1)));
        list.add(MapEntry.create(LocalDateTime.of(2000,4,1,1,1,1),LocalDateTime.of(2000,5,1,1,1,1)));
        list.add(MapEntry.create(LocalDateTime.of(2000,6,1,1,1,1),LocalDateTime.of(2000,7,1,1,1,1)));
        List<MapEntry<LocalDateTime, LocalDateTime>> mapEntries = TimeUtils.mergeTime(list);
        System.out.println(mapEntries);
    }
}