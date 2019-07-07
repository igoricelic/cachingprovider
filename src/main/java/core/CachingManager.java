package core;

import core.factory.CacheableObjectFactory;
import javassist.util.proxy.ProxyObject;
import proxy.DynamicAdvice;
import proxy.DynamicClassRegistry;

import java.lang.reflect.InvocationTargetException;

public class CachingManager implements CacheableObjectFactory {

    private final RegionProvider regionProvider;

    private final DynamicClassRegistry dynamicClassRegistry;

    CachingManager (RegionProvider regionProvider) {
        this.regionProvider = regionProvider;
        this.dynamicClassRegistry = new DynamicClassRegistry(regionProvider);
    }

    @Override
    public <T> T create(Class<T> clazz) {
        var constructor = dynamicClassRegistry.getDynamicConstuctor(clazz);
        try {
            T instance = (T) constructor.newInstance();
            ((ProxyObject) instance).setHandler(new DynamicAdvice(regionProvider));
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
