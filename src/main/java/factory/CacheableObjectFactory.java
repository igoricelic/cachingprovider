package factory;

/**
 *
 */
public interface CacheableObjectFactory {

    <T> T create (Class<T> clazz);

}
