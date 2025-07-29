package AbstractDocument;

import java.util.Map;

public class Part extends AbstractDocument implements HasModel, HasPrice {
    public Part(Map<String, Object> properties) {
        super(properties);
    }
}
