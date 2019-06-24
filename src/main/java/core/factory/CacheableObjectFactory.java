package core.factory;

/**
 *
 */
public interface CacheableObjectFactory {

    <T> T create (Class<T> clazz);

}
