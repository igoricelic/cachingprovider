package core.model;

import core.factory.CacheableObjectFactory;
import javassist.util.proxy.ProxyObject;
import proxy.ClassRegistry;
import proxy.javassist.DynamicAdvice;
import proxy.javassist.DynamicClassProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author igoricelic
 */
public class CachingManager implements CacheableObjectFactory {

    private final RegionProvider regionProvider;

    private final ClassRegistry classRegistry;

    public CachingManager (RegionProvider regionProvider) {
        this.regionProvider = regionProvider;
        this.classRegistry = new DynamicClassProvider();
    }

    @Override
    public <T> T create(Class<T> clazz) {
        var constructor = getDefaultInitializer(clazz);
        try {
            T instance = (T) constructor.newInstance();
            ((ProxyObject) instance).setHandler(new DynamicAdvice(regionProvider));
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> Constructor<?> getDefaultInitializer(Class<T> clazz) {
        if(!classRegistry.contains(clazz)) {
            regionProvider.validate(clazz);
            classRegistry.createChildClass(clazz);
        }
        return classRegistry.getDefaultInitializer(clazz);
    }

}
