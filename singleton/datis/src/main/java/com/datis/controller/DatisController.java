package com.datis.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.datis.domain.DatisInfo;
import com.datis.domain.MqLogs;
import com.datis.service.IMqLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
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
            "        <Sender>datis</Sender>\n" +
            "        <SN>#UUID#</SN>\n" +
            "        <SendTime>#SendTime#</SendTime>\n" +
            "    </Header>\n" +
            "    <Body>\n" +
            "        <AtisInfo>\n" +
            "            <ID>#UUID#</ID>\n" +
            "            <AtisTime>#UTCTime#</AtisTime>\n" +
            "            <AtisOrder>#DepOrder#</AtisOrder>\n" +
            "            <Temperature>0</Temperature>\n" +
            "            <QNH>#DepQNH#</QNH>\n" +
            "            <CreateTime>#CSTTime#</CreateTime>\n" +
            "            <DepAtisOrder>#DepOrder#</DepAtisOrder>\n" +
            "            <DepTemperature>0</DepTemperature>\n" +
            "            <DepQNH>#DepQNH#</DepQNH>\n" +
            "            <ArrAtisOrder>#ArrOrder#</ArrAtisOrder>\n" +
            "            <ArrTemperature>0</ArrTemperature>\n" +
            "            <ArrQNH>#ArrQNH#</ArrQNH>\n" +
            "        </AtisInfo>\n" +
            "    </Body>\n" +
            "</AtisInfoInsertMsg>";

    /**
     * 国际时间格式
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    /**
     * 缓存文件路径
     */
    @Value("${datis.cache-path}")
    private String cachePath;

    /**
     * 数字通播API地址
     */
    @Value("${datis.api-url}")
    private String apiUrl;


    /**
     * 定时调用数字通播API
     * 30秒执行一次
     */
    @Scheduled(fixedRate = 30000)
    public void datisJob() {
        // 获取数字通播XML
        String xml = getDatisXml();

        // 解析XML提取关键因素
        DatisInfo info = parsingXml(xml);

        // 判空
        if(BeanUtil.isNotEmpty(info)){
            String depOrder = info.getDepOrder();
            String arrOrder = info.getArrOrder();
            String depQnh = info.getDepQnh();
            String arrQnh = info.getArrQnh();
            String utcTime = info.getUtcTime();
            String cstTime = info.getCstTime();
            String sendTime = info.getSendTime();
            String id = RandomUtil.randomNumbers(6);

            // 替换占位符
            String handlerAtisInfoMsg = AtisInfoMsg
                    .replaceAll("#UUID#", id)
                    .replaceAll("#UTCTime#", utcTime)
                    .replaceAll("#CSTTime#", cstTime)
                    .replaceAll("#SendTime#", sendTime)
                    .replaceAll("#DepOrder#", depOrder)
                    .replaceAll("#ArrOrder#", arrOrder)
                    .replaceAll("#DepQNH#", depQnh)
                    .replaceAll("#ArrQNH#", arrQnh);

            // 判断报文是否重复
            if(!isHandler(handlerAtisInfoMsg)){
                // 压缩XML
                String insertAtisInfoMsg = compress(handlerAtisInfoMsg);
                if(StrUtil.isNotBlank(insertAtisInfoMsg)){
                    // 写入缓存文件
                    inputFile(insertAtisInfoMsg);
                    // 写入数据库中间表
                    MqLogs pojo = new MqLogs();
                    pojo.setMsg(insertAtisInfoMsg);
                    LocalDateTime time = LocalDateTime.parse(sendTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusHours(8);
                    pojo.setCreateTime(time);
                    pojo.setLastModifiedTime(time);
                    mqLogsService.save(pojo);
                }
            }
        }
    }


    /**
     * 获取数字通播XML
     */
    public String getDatisXml(){
        HttpResponse response = HttpRequest.get(apiUrl).execute();
        int status = response.getStatus();
        String responseBody = response.body();
        if (status != 200) {
            log.error("获取数字通播数据异常:{}",responseBody);
        }
        return responseBody;
    }


    /**
     * 将报文字符串解析为 Map（类似分词）
     */
    public DatisInfo parsingXml(String xml) {
        try {
            DatisInfo info = new DatisInfo();

            // 解析为 Document 对象
            Document doc = XmlUtil.parseXml(xml);

            // 获取所有 Airport 节点
            NodeList airportNodes = doc.getElementsByTagName("Airport");

            // 遍历所有 Airport
            for (int i = 0; i < airportNodes.getLength(); i++) {
                Element airport = (Element) airportNodes.item(i);

                // 获取属性 code 和 type
                String code = airport.getAttribute("code");
                String type = airport.getAttribute("type");

                // 只解析 code="ZGNN" 且 type="DEP"/"ARR" 的节点
                if ("ZGNN".equals(code) && "DEP".equals(type)) {
                    // 获取 Order 元素的文本内容
                    String order = XmlUtil.elementText(airport, "Order");

                    // 获取 Order 元素的文本内容
                    String qnhText = XmlUtil.elementText(airport, "QNH");

                    // 获取 Order 元素的文本内容
                    String qfeText = XmlUtil.elementText(airport, "QFE");

                    // 获取 UpdateTime 元素的文本内容
                    String updateTime = XmlUtil.elementText(airport, "UpdateTime");

                    // 如果为停播则停止解析
                    if(StrUtil.isBlank(order) || !order.matches("^[A-Za-z]+$") || StrUtil.isBlank(qnhText) || StrUtil.isBlank(qfeText) || StrUtil.isBlank(updateTime)){
                        return null;
                    }

                    // 格式化为国际时间格式
                    LocalDateTime utcTime = LocalDateTime.parse(updateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    // 加上时区信息（如 +08:00）
                    ZoneOffset offset = ZoneOffset.ofHours(8);
                    OffsetDateTime offsetUtcTime = utcTime.atOffset(offset);
                    // 格式化为 2024-05-23T15:02:26.613+08:00
                    String utcTimeStr = offsetUtcTime.format(formatter);

                    // 转换为北京时间
                    LocalDateTime cstTime = utcTime.plusHours(8);
                    OffsetDateTime offsetCreateTime = cstTime.atOffset(offset);
                    // 格式化为 2024-05-23T15:02:26.613+08:00
                    String cstTimeStr = offsetCreateTime.format(formatter);

                    // 获取 QFE 节点
                    Element qfe = XmlUtil.getElement(airport, "QFE");

                    // 获取 QFE 下的第一个 nd 节点
                    NodeList ndList = qfe.getElementsByTagName("nd");
                    String firstTdz = null;
                    if (ndList.getLength() > 0) {
                        Element firstNd = (Element) ndList.item(0);
                        firstTdz = XmlUtil.elementText(firstNd, "TDZ");
                    }

                    info.setDepOrder(order);
                    info.setDepQnh(firstTdz);
                    info.setUtcTime(utcTimeStr);
                    info.setCstTime(cstTimeStr);
                    info.setSendTime(updateTime);
                }

                // 解析 code="ZGNN" 且 type="ARR" 的节点
                if ("ZGNN".equals(code) && "ARR".equals(type)) {
                    // 获取 Order 元素的文本内容
                    String order = XmlUtil.elementText(airport, "Order");

                    // 获取 Order 元素的文本内容
                    String qnhText = XmlUtil.elementText(airport, "QNH");

                    // 获取 Order 元素的文本内容
                    String qfeText = XmlUtil.elementText(airport, "QFE");

                    // 获取 UpdateTime 元素的文本内容
                    String updateTime = XmlUtil.elementText(airport, "UpdateTime");

                    // 如果为停播则停止解析
                    if(StrUtil.isBlank(order) && !order.matches("^[A-Za-z]+$") || StrUtil.isBlank(qnhText) || StrUtil.isBlank(qfeText) || StrUtil.isBlank(updateTime)){
                        return null;
                    }

                    // 获取 QFE 节点
                    Element qfe = XmlUtil.getElement(airport, "QFE");

                    // 获取 QFE 下的第一个 nd 节点
                    NodeList ndList = qfe.getElementsByTagName("nd");
                    String firstTdz = null;
                    if (ndList.getLength() > 0) {
                        Element firstNd = (Element) ndList.item(0);
                        firstTdz = XmlUtil.elementText(firstNd, "TDZ");
                    }

                    info.setArrOrder(order);
                    info.setArrQnh(firstTdz);
                }
            }
            return info;
        } catch (Exception e){
            log.error("解析XML失败:{}",xml);
            log.error("异常信息",e);
        }
        return null;
    }


    /**
     * 将原始字符串压缩为 GZIP 后再进行 Base64 编码
     */
    public String compress(String originalStr) {
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
            log.error("压缩失败",e);
        }
        return null;
    }

    /**
     * 将 Base64 编码的 GZIP 压缩字符串解压为原始内容
     */
    public String decompress(String base64Str) {
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
            log.error("解压失败",e);
        }
        return null;
    }


    /**
     * 判断内容和文件缓存的是否一致
     */
    public boolean isHandler(String xml) {
        byte[] bytes = FileUtil.readBytes(cachePath);
        if(ArrayUtil.isEmpty(bytes)){
            return false;
        }
        String cache = new String(bytes, StandardCharsets.UTF_8);
        String cacheXml = decompress(cache);
        // 删除UUID标签
        xml = xml.replaceAll("<SN>.*?</SN>", "")
                .replaceAll("<ID>.*?</ID>", "");
        cacheXml = cacheXml.replaceAll("<SN>.*?</SN>", "")
                .replaceAll("<ID>.*?</ID>", "");
        return xml.equals(cacheXml);
    }


    /**
     * 将报文写入文件缓存起来
     */
    public void inputFile(String xml){
        try (FileWriter writer = new FileWriter(cachePath, false)) {
            writer.write(xml);
        } catch (IOException e) {
            log.error("写入数字通播数据到缓存文件异常",e);
        }
    }
}
