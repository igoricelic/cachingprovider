package specification;

/**
 *
 */
public interface Provider {

    <T> void set (String key, T value);

    <T> T get (String key, Class<T> clazz);

    boolean contains (String key);

}
