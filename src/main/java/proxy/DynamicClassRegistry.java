package proxy;

import javassist.util.proxy.ProxyFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicClassRegistry {

    private Map<Class<?>, Class<?>> registry = new ConcurrentHashMap<>();

    public static <T> Class<?> getDynamicClass (Class<T> clazz) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        Class<?> newClazz = proxyFactory.createClass();

        return newClazz;
    }

//    private static <T> Class<?> createClass (Class<T> clazz) {
//        try (clazz.getDeclaredConstructor()) {
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setSuperclass(clazz);
//        Class<?> newClazz = proxyFactory.createClass();
//        return null;
//    }

}
