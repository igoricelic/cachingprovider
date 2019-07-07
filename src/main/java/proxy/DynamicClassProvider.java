package proxy;

import core.RegionProvider;
import exceptions.NoDefaultConstructorException;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicClassProvider implements ClassRegistry {

    private Map<Class<?>, Constructor<?>> registry;

    public DynamicClassProvider() {
        this.registry = new ConcurrentHashMap<>();
    }

    @Deprecated
    public <T> Constructor<?> getDynamicConstuctor (Class<T> clazz) {
        if(!registry.containsKey(clazz)) {
            var newClass = createClass(clazz);
            try {
                registry.put(clazz, newClass.getConstructor());
            } catch (NoSuchMethodException e) {
                throw new NoDefaultConstructorException();
            }
        }
        return registry.get(clazz);
    }

    @Deprecated
    private Class<?> createClass (Class<?> clazz) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        return proxyFactory.createClass();
    }

    @Override
    public <T> Constructor<?> getDefaultInitializer(Class<T> clazz) {
        return  registry.get(clazz);
    }

    @Override
    public <T> Class<?> createChildClass(Class<T> clazz) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        var newClazz = proxyFactory.createClass();
        try {
            registry.put(clazz, newClazz.getConstructor());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return newClazz;
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return registry.containsKey(clazz);
    }

}
