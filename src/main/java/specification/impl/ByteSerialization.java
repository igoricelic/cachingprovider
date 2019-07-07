package specification.impl;

import specification.ObjectSerializeProvider;

import java.io.*;
import java.util.Base64;

/**
 *
 */
public class ByteSerialization implements ObjectSerializeProvider {

    @Override
    public String toString(Serializable serializableObject) throws IOException {
        var baos = new ByteArrayOutputStream();
        var oos = new ObjectOutputStream(baos);
        oos.writeObject( serializableObject );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    @Override
    public <T extends Serializable> T toObject(String valueAsString, Class<T> clazz) throws IOException {
        try {
            byte [] data = Base64.getDecoder().decode(valueAsString);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            ois.close();
            var result = ois.readObject();
            return clazz.cast(result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
