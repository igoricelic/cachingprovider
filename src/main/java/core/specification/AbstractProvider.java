package core.specification;

import core.model.metadata.KeyMetadata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractProvider implements Provider {

    private Map<String, KeyMetadata> metadata;

    protected AbstractProvider() {
        metadata = new ConcurrentHashMap<>();
    }

    @Override
    public Map<String, KeyMetadata> metadata() {
        return metadata;
    }

    @Override
    public boolean clear(String key) {
        if(metadata.containsKey(key)) {
            metadata.remove(key);
            return true;
        }
        return false;
    }
}
