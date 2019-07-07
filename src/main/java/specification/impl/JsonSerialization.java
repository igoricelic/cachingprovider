package specification.impl;

import org.codehaus.jackson.map.ObjectMapper;
import specification.ObjectSerializeProvider;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 */
public class JsonSerialization implements ObjectSerializeProvider {

    @Override
    public String toString(Serializable serializableObject) throws IOException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(serializableObject);
    }

    @Override
    public <T extends Serializable> T toObject(String valueAsString, Class<T> clazz) throws IOException {
        var objectMapper = new ObjectMapper();
        return objectMapper.readValue(valueAsString, clazz);
    }
}
