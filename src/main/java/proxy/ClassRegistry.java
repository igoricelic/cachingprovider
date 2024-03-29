package proxy;

import java.lang.reflect.Constructor;

/**
 * @author igoricelic
 */
public interface ClassRegistry {

    <T> Constructor<?> getDefaultInitializer (Class<T> clazz);

    <T> Class<?> createChildClass (Class<T> clazz);

    boolean contains (Class<?> clazz);

}
