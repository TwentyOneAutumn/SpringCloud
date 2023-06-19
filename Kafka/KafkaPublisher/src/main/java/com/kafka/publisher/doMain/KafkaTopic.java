package com.kafka.publisher.doMain;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KafkaTopic {
    public static final String TEST1 = "test1";
    public static final List<String> topicList = Arrays.stream(new String[]{
                    "test1"
            })
        .collect(Collectors.toList());

    /**
     * 判断是否声明该Topic
     * @param topic topic名称
     * @return true:包含 false:不包含
     */
    public static boolean authTopic(String topic){
        return topicList.contains(topic);
    }

}
