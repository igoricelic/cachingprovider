package core.specification;

import java.io.Serializable;

/**
 * @author igoricelic
 */
public interface Provider {

    <T extends Serializable> void set (String key, T value);

    <T extends Serializable> T get (String key, Class<T> clazz);

    boolean contains (String key);

}
