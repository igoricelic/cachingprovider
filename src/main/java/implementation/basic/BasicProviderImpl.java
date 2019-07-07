package implementation.basic;

import specification.Provider;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public final class BasicProviderImpl implements Provider {

    private Map<String, Object> dataGrid;

    public BasicProviderImpl() {
        this.dataGrid = new ConcurrentHashMap<>();
    }

    @Override
    public <T extends Serializable> void set(String key, T value) {
        dataGrid.put(key, value);
    }

    @Override
    public <T extends Serializable> T get(String key, Class<T> clazz) {
        return clazz.cast(dataGrid.get(key));
    }

    @Override
    public boolean contains(String key) {
        return dataGrid.containsKey(key);
    }

}
