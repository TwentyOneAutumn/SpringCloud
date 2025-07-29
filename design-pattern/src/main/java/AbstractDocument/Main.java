package AbstractDocument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Object> part1 = new HashMap<>();
        part1.put("model", "发动机");
        part1.put("price", 5000L);

        Map<String, Object> part2 = new HashMap<>();
        part2.put("model", "轮胎");
        part2.put("price", 1200L);

        List<Map<String, Object>> parts = Arrays.asList(part1, part2);

        Map<String, Object> carProperties = new HashMap<>();
        carProperties.put("model", "比亚迪汉");
        carProperties.put("price", 250000L);
        carProperties.put("parts", parts);

        Car car = new Car(carProperties);


        car.getParts().forEach(part -> {
            System.out.println("部件: " + part.getModel().orElse("未知"));
            System.out.println("价格: " + part.getPrice().orElse(0L));
        });
    }
}
