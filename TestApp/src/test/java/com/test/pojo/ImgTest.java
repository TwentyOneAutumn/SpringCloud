package com.test.pojo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootTest
public class ImgTest {

    @Test
    void imgToBase64() throws Exception {
        String imagePath = "path/to/your/image.jpg"; // 图片的路径

        try {
            // 读取图片文件并将其转换为字节数组
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

            // 将字节数组进行Base64编码
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // 打印Base64编码字符串
            System.out.println(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
