//package com.datis.controller;
//
//import com.datis.service.IMqLogsService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequestMapping
//public class TestController {
//
//    @Autowired
//    private IMqLogsService mqLogsService;
//
//    /**
//     * 报文模板
//     */
//    private final String DatisMessage =
//            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                    "<ATIS V=\"3.0\" >\n" +
//                    "  <Info>\n" +
//                    "    <Airport code=\"ZGNN\" type=\"DEP\">\n" +
//                    "      <Order>A</Order>\n" +
//                    "      <QNH>1011</QNH>\n" +
//                    "      <QFE>\n" +
//                    "        <nd RWY=\"23\">\n" +
//                    "          <TDZ>997</TDZ>\n" +
//                    "          <MID></MID>\n" +
//                    "          <END>996</END>\n" +
//                    "        </nd>\n" +
//                    "        <nd RWY=\"24\">\n" +
//                    "          <TDZ>996</TDZ>\n" +
//                    "          <MID></MID>\n" +
//                    "          <END>996</END>\n" +
//                    "        </nd>\n" +
//                    "      </QFE>\n" +
//                    "      <UpdateTime>2025-10-09 08:03:57</UpdateTime>\n" +
//                    "      <ExpiredTime>2025-10-09 09:08:00</ExpiredTime>\n" +
//                    "    </Airport>\n" +
//                    "    <Airport code=\"ZGNN\" type=\"ARR\">\n" +
//                    "      <Order>A</Order>\n" +
//                    "      <QNH>1011</QNH>\n" +
//                    "      <QFE>\n" +
//                    "        <nd RWY=\"23\">\n" +
//                    "          <TDZ>996</TDZ>\n" +
//                    "          <MID></MID>\n" +
//                    "          <END>996</END>\n" +
//                    "        </nd>\n" +
//                    "        <nd RWY=\"24\">\n" +
//                    "          <TDZ>996</TDZ>\n" +
//                    "          <MID></MID>\n" +
//                    "          <END>996</END>\n" +
//                    "        </nd>\n" +
//                    "      </QFE>\n" +
//                    "      <UpdateTime>2025-10-09 08:03:57</UpdateTime>\n" +
//                    "      <ExpiredTime>2025-10-09 09:08:00</ExpiredTime>\n" +
//                    "    </Airport>\n" +
//                    "  </Info>\n" +
//                    "</ATIS>";
//
//    private final String DatisMessage2 =
//            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//            "<ATIS V=\"3.0\" >\n" +
//            "  <Info>\n" +
//            "    <Airport code=\"ZGNN\" type=\"DEP\">\n" +
//            "      <Order>-</Order>\n" +
//            "      <QNH>/>\n" +
//            "      <QFE/>\n" +
//            "      <UpdateTime/>\n" +
//            "      <ExpiredTime/>\n" +
//            "    </Airport>\n" +
//            "  </Info>\n" +
//            "</ATIS>";
//
//
//    @GetMapping(value = "/getDatisXml", produces = MediaType.APPLICATION_XML_VALUE)
//    public String datisXml() {
//        return DatisMessage2;
//    }
//}
