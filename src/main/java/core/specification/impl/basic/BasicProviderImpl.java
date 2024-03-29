package core.specification.impl.basic;

import core.specification.AbstractProvider;
import core.specification.ObjectSerializeProvider;
import core.specification.impl.ByteSerialization;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author igoricelic
 */
public final class BasicProviderImpl extends AbstractProvider {

    private final Map<String, String> dataGrid;

    private final ObjectSerializeProvider serializeProvider;

    public BasicProviderImpl() {
        this.dataGrid = new ConcurrentHashMap<>();
        this.serializeProvider = new ByteSerialization();
    }

    @Override
    public <T extends Serializable> void set(String key, T value) {
        try {
            var valueAsString = serializeProvider.toString(value);
            dataGrid.put(key, valueAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends Serializable> T get(String key, Class<T> clazz) {
        var valueAsString = dataGrid.get(key);
        try {
            return serializeProvider.toObject(valueAsString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(String key) {
        return dataGrid.containsKey(key);
    }

    @Override
    public boolean clear(String key) {
        dataGrid.remove(key);
        return super.clear(key);
    }
}
