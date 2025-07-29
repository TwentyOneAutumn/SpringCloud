package com.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 汉字转拼音工具类
 */
public class HanYuPinyinUtil {

    @SneakyThrows
    public static String convert(String text, boolean ignoreNotPinYin, boolean isUpperCase) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(isUpperCase ? HanyuPinyinCaseType.UPPERCASE : HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String pinyin = PinyinHelper.toHanYuPinyinString(text, format, "", true);
        if(StrUtil.isNotBlank(pinyin)){
            // 去除非拼音的其他字符
            if(ignoreNotPinYin){
                pinyin = pinyin.trim().replaceAll("[^a-zA-Z]","");
            }
        }
        return pinyin;
    }
}
