package proxy;

import core.RegionProvider;
import exceptions.NoDefaultConstructorException;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicClassRegistry {

    private final RegionProvider regionProvider;

    private Map<Class<?>, Constructor<?>> registry;

    public DynamicClassRegistry(RegionProvider regionProvider) {
        this.regionProvider = regionProvider;
        this.registry = new ConcurrentHashMap<>();
    }

    public <T> Constructor<?> getDynamicConstuctor (Class<T> clazz) {
        if(!registry.containsKey(clazz)) {
            regionProvider.validate(clazz);
            var newClass = createClass(clazz);
            try {
                registry.put(clazz, newClass.getConstructor());
            } catch (NoSuchMethodException e) {
                throw new NoDefaultConstructorException();
            }
        }
        return registry.get(clazz);
    }

    private Class<?> createClass (Class<?> clazz) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        return proxyFactory.createClass();
    }

}
