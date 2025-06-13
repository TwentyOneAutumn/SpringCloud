package com.datis.controller;

import com.datis.domain.MqLogs;
import com.datis.domain.RunDto;
import com.datis.service.IMqLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
@RestController
@RequestMapping
public class DatisController {

    @Autowired
    private IMqLogsService mqLogsService;

    /**
     * 报文模板
     */
    private final String AtisInfoMsg =
            "<AtisInfoInsertMsg>\n" +
            "    <Header>\n" +
            "        <MsgType>AtisInfo</MsgType>\n" +
            "        <OperationType>I</OperationType>\n" +
            "        <Sender>toms</Sender>\n" +
            "        <SN>#FILTIM#</SN>\n" +
            "        <SendTime>#SendTime#</SendTime>\n" +
            "    </Header>\n" +
            "    <Body>\n" +
            "        <AtisInfo>\n" +
            "            <ID>#FILTIM#</ID>\n" +
            "            <AtisTime>#AtisTime#</AtisTime>\n" +
            "            <AtisOrder>#DepAtis#</AtisOrder>\n" +
            "            <Temperature>22</Temperature>\n" +
            "            <QNH>0</QNH>\n" +
            "            <CreateTime>#CreateTime#</CreateTime>\n" +
            "            <DepAtisOrder>#DepAtis#</DepAtisOrder>\n" +
            "            <DepTemperature>0</DepTemperature>\n" +
            "            <DepQNH>0</DepQNH>\n" +
            "            <ArrAtisOrder>#ArrAtis#</ArrAtisOrder>\n" +
            "            <ArrTemperature>0</ArrTemperature>\n" +
            "            <ArrQNH>0</ArrQNH>\n" +
            "        </AtisInfo>\n" +
            "    </Body>\n" +
            "</AtisInfoInsertMsg>";

    
    @GetMapping("/run")
    public String run(RunDto dto) {
        String message = dto.getMessage();
        Map<String, String> result = parseToMap(message);
        // 来源
        String source = result.get("SOURCE");
        // 发送时间,此处当做唯一ID使用
        String id = result.get("FILTIM");
        // 离港通播代码
        String depAtis = result.get("DEPATIS");
        // 进港通播代码
        String arrAtis = result.get("ARRATIS");

        LocalDateTime atisTime = resolveUtcTime(id);
        LocalDateTime createTime = LocalDateTime.now();
        String sendTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 加上时区信息（如 +08:00）
        ZoneOffset offset = ZoneOffset.ofHours(8);
        OffsetDateTime offsetCreateTime = createTime.atOffset(offset);
        OffsetDateTime offsetAtisTime = atisTime.atOffset(offset);

        // 格式化为 2024-05-23T15:02:26.613+08:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String createTimeStr = offsetCreateTime.format(formatter);
        String atisTimeStr = offsetAtisTime.format(formatter);

        // 替换占位符
        String handlerAtisInfoMsg = AtisInfoMsg
                .replaceAll("#FILTIM#", id)
                .replaceAll("#SendTime#", sendTime)
                .replaceAll("#AtisTime#", atisTimeStr)
                .replaceAll("#CreateTime#", createTimeStr)
                .replaceAll("#DepAtis#", depAtis)
                .replaceAll("#ArrAtis#", arrAtis);

        String insertAtisInfoMsg = compress(handlerAtisInfoMsg);

        // 写入中间表
        MqLogs pojo = new MqLogs();
        pojo.setMsg(insertAtisInfoMsg);
        pojo.setSource(source);
        mqLogsService.save(pojo);

        return "操作成功";
    }

    /**
     * 将报文字符串解析为 Map（类似分词）
     */
    public Map<String, String> parseToMap(String message) {
        Map<String, String> map = new LinkedHashMap<>();

        // 去除头尾标志
        message = message.replace("NNNN", "").trim();
        message = "-CODE " + message;

        // 以 - 分割字段
        String[] tokens = message.split("-");

        for (String token : tokens) {
            token = token.trim();
            int spaceIndex = token.indexOf(" ");
            if (spaceIndex > 0) {
                String key = token.substring(0, spaceIndex);
                String value = token.substring(spaceIndex + 1).trim();
                map.put(key, value);
            }
        }

        return map;
    }

    /**
     * 将UTC LocalTime 填充年月日并转换为 LocalDateTime
     */
    public LocalDateTime resolveUtcTime(String time) {
        LocalTime utcTimeOnly = LocalTime.parse(time,DateTimeFormatter.ofPattern("HHmmss"));
        ZonedDateTime nowBjt = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime nowUtc = nowBjt.withZoneSameInstant(ZoneOffset.UTC);
        LocalDate utcDate = nowUtc.toLocalDate();
        LocalDateTime candidateTime = LocalDateTime.of(utcDate, utcTimeOnly);

        // 如果拼接的时间比当前 UTC 时间晚 3小时 以上，说明是昨天
        if (candidateTime.isAfter(nowUtc.toLocalDateTime().plusHours(3))) {
            candidateTime = candidateTime.minusDays(1);
        }

        return candidateTime;
    }


    /**
     * 将原始字符串压缩为 GZIP 后再进行 Base64 编码
     */
    public static String compress(String originalStr) {
        if (originalStr == null || originalStr.isEmpty()) {
            return "";
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOut = new GZIPOutputStream(byteArrayOutputStream)) {

            // 写入原始字符串的字节内容（使用UTF-8编码）
            gzipOut.write(originalStr.getBytes(StandardCharsets.UTF_8));
            gzipOut.finish();

            // 将压缩后的字节数组进行Base64编码
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("压缩失败", e);
        }
    }

    /**
     * 将 Base64 编码的 GZIP 压缩字符串解压为原始内容
     */
    public static String decompress(String base64Str) {
        if (base64Str == null || base64Str.isEmpty()) {
            return "";
        }

        byte[] compressedBytes = Base64.getDecoder().decode(base64Str);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedBytes);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
             InputStreamReader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
             BufferedReader in = new BufferedReader(reader)) {

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString().trim(); // 去除最后一个换行符

        } catch (IOException e) {
            throw new RuntimeException("解压失败", e);
        }
    }
}
