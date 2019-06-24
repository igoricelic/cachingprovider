package core;

/**
 *
 */
interface CacheableObjectFactory {

    <T> T create (Class<T> clazz);

}
