package AbstractDocument;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbstractDocument implements Document {

    private final Map<String, Object> properties;

    public AbstractDocument(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public void put(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public Set<String> keys() {
        return properties.keySet();
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        Object value = get(key);
        if (value instanceof List<?>) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) value;
            return list.stream().map(constructor);
        }
        return Stream.empty();
    }
}
