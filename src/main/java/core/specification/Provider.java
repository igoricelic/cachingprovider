package core.specification;

import core.model.metadata.KeyMetadata;

import java.io.Serializable;
import java.util.Map;

/**
 * @author igoricelic
 */
public interface Provider {

    <T extends Serializable> void set (String key, T value);

    <T extends Serializable> T get (String key, Class<T> clazz);

    boolean contains (String key);

    boolean clear (String key);

    Map<String, KeyMetadata> metadata ();

}
