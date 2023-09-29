package com.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.test.doMain.SysTest;
import com.test.service.ISysTestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Autowired
    private ISysTestService sysTestService;

    @Test
    @Transactional
    public void test() throws Exception {
        List<String> idList = Arrays.asList(new String[]{"2", "1"});
        // 获取集合
        List<SysTest> list = sysTestService.list(new LambdaQueryWrapper<SysTest>()
                .eq(SysTest::getAge,1) // select * from table where age = 111
                .ne(SysTest::getCode,"111") // select * from table where age != 111
                .ge(SysTest::getCode,"111") // select * from table where age >= 111
                .between(SysTest::getAge,1,18) // select * from table where age between 1 and 18
                .in(SysTest::getId,idList) // select * from table where id in("2","1")
                .groupBy(false,SysTest::getCode,SysTest::getId) // select * from table group by code,id
                .orderByAsc(SysTest::getId) // // select * from table order by id asc
                .and(xxx -> {
                    xxx.eq(SysTest::getId,"1")
                        .eq(SysTest::getAge,2);
                    // select * from table where age = 1 and ( id = 1 and age = 2)
                })
        );
        // 获取唯一一个符合条件的对象，返回的记录必须是1条或者0，如果返回0条则对象为空，如果返回超过1条则抛异常
        sysTestService.getOne(new LambdaQueryWrapper<SysTest>()
                .eq(SysTest::getAge,11)
        );
        // 等同于 select count(*) from table where ...
        long count = sysTestService.count();
        // 插入对象，参数为实体类
        sysTestService.save(new SysTest());
        // 批量插入，参数为实体类集合，如果有一个插入失败，则抛出异常，全部插入都失败
        sysTestService.saveBatch(new ArrayList<>());
        // 更新 update table set code = 111 where age = 18
        sysTestService.update(new LambdaUpdateWrapper<SysTest>()
                .eq(SysTest::getAge,18)
                .set(SysTest::getCode,"111")
        );
        // 参数可以是实体类或者字符串(根据主键类型)
        sysTestService.updateById(new SysTest());
        // 批量根据ID更新，数组或者集合类型
        sysTestService.updateBatchById(new ArrayList<>());
        // 同上 delete from table where age = 11
        sysTestService.remove(new LambdaQueryWrapper<SysTest>()
                .eq(SysTest::getAge,11)
        );
        // 根据ID删除，传入实体类或者id
        sysTestService.removeById(new SysTest());
        // 批量删除，同上
        sysTestService.removeBatchByIds(new ArrayList<>());

    }
}
