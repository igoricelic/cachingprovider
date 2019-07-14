package core.specification;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author igoricelic
 */
public interface ObjectSerializeProvider {

    String toString(Serializable serializableObject) throws IOException;

    <T extends Serializable> T toObject(String valueAsString, Class<T> clazz) throws IOException;

}
