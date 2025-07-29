package AbstractDocument;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Document {

    void put(String key, Object value);

    Object get(String key);

    Set<String> keys();

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
