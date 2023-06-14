package com.test;

import com.core.Interface.NotNullArgs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class NotNullTest {

    @Test
    public void test(){
        List<String> args = new ArrayList<>();
        args.add("foo");
        args.add("bar");
        Stream<String> stream = args.stream();
        Stream<String> bar = stream.filter(item -> item.equals("bar"));
        List<String> collect = bar.collect(Collectors.toList());
        System.out.println(collect);
    }
}
