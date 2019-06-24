package implementation.redis;

import org.codehaus.jackson.map.ObjectMapper;
import redis.clients.jedis.Jedis;
import specification.Provider;

import java.io.IOException;

/**
 *
 */
public final class RedisProviderImpl implements Provider {

    private Jedis jedis;

    private ObjectMapper objectMapper;

    public RedisProviderImpl() {
        this.jedis = new Jedis();
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public <T> void set(String key, T value) {
        try {
            String valueAsString = objectMapper.writeValueAsString(value);
            jedis.set(key, valueAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String valueAsString = jedis.get(key);
        try {
            return objectMapper.readValue(valueAsString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(String key) {
        return jedis.exists(key);
    }
}
