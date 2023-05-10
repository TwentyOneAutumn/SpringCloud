//package com.atmb.generator.controller;
//
////import com.atmb.common.core.web.page.PageResult;
//import com.atmb.generator.service.SysGeneratorService;
////import io.swagger.annotations.Api;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RestController
////@Api(tags = "API generator")
//@RequestMapping("/generator")
//public class SysGeneratorController {
//
//    @Autowired
//    private SysGeneratorService sysGeneratorService;
//
//    /**
//     * 生成代码FileUtil
//     */
//    @GetMapping("/code")
//    public void makeCode(String tables,String dbName, HttpServletResponse response) throws IOException {
//        byte[] data = sysGeneratorService.generatorCode(tables.split(","),dbName);
//        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"generator.zip\"");
//        response.addHeader("Content-Length", "" + data.length);
//        response.setContentType("application/octet-stream; charset=UTF-8");
//        IOUtils.write(data, response.getOutputStream());
//    }
//}
