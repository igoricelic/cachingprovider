package proxy.javassist;

import javassist.util.proxy.ProxyFactory;
import proxy.ClassRegistry;
import util.impl.Exceptions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author igoricelic
 */
public class DynamicClassProvider implements ClassRegistry {

    private Map<Class<?>, Constructor<?>> registry;

    public DynamicClassProvider() {
        this.registry = new ConcurrentHashMap<>();
    }

    @Override
    public <T> Constructor<?> getDefaultInitializer(Class<T> clazz) {
        return  registry.get(clazz);
    }

    @Override
    public <T> Class<?> createChildClass(Class<T> clazz) {
        if(Modifier.isFinal(clazz.getModifiers())) {
            throw new Exceptions.InvalidModifierException("Class can't be final!");
        }
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        var newClazz = proxyFactory.createClass();
        try {
            registry.put(clazz, newClazz.getConstructor());
        } catch (NoSuchMethodException e) {
            throw new Exceptions.NoDefaultConstructorException(String.format("Error in class: %s: Not found default constructor!", clazz.getName()));
        }
        return newClazz;
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return registry.containsKey(clazz);
    }

}
