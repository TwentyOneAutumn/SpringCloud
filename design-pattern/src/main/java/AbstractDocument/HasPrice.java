package AbstractDocument;

import java.util.Optional;

public interface HasPrice extends Document {

    String PROPERTY = "price";

    default Optional<Long> getPrice() {
        return Optional.ofNullable((Long) get(PROPERTY));
    }
}
