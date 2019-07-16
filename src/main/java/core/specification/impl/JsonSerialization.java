package core.specification.impl;

import org.codehaus.jackson.map.ObjectMapper;
import core.specification.ObjectSerializeProvider;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author igoricelic
 */
public final class JsonSerialization implements ObjectSerializeProvider {

    @Override
    public String toString(Serializable serializableObject) throws IOException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(serializableObject);
    }

    @Override
    public <T> T toObject(String valueAsString, Class<T> clazz) throws IOException {
        var objectMapper = new ObjectMapper();
        return objectMapper.readValue(valueAsString, clazz);
    }
}
