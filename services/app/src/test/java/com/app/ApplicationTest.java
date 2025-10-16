package com.app;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
public class ApplicationTest {

    @Test
    public void test2() throws Exception {
        String time = "202511.V2";
        String time2 = "2025-10-10";
        String time3 = time.replace(".", "期") + "(" + time2.substring(5) + ")";
        System.out.println(time3);
    }
}
