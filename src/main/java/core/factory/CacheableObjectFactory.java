package core.factory;

/**
 * @author igoricelic
 */
public interface CacheableObjectFactory {

    <T> T create (Class<T> clazz);

}
