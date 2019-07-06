package proxy;

import exceptions.NoDefaultConstructorException;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicClassRegistry {

    private Map<Class<?>, Constructor<?>> registry = new ConcurrentHashMap<>();

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

    private Class<?> createClass (Class<?> clazz) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        return proxyFactory.createClass();
    }

}
