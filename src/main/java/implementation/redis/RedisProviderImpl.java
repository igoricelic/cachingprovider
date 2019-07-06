package implementation.redis;

import org.codehaus.jackson.map.ObjectMapper;
import redis.clients.jedis.Jedis;
import specification.Provider;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
public final class RedisProviderImpl implements Provider {

    private BlockingQueue<Jedis> connectionPool;

    private ObjectMapper objectMapper;

    public RedisProviderImpl(RedisConfig config) {
        this.objectMapper = new ObjectMapper();
        this.connectionPool = JedisConnectionProvider.getPool(config);
    }


    @Override
    public <T> void set(String key, T value) {
        try {
            var connection = connectionPool.take();
            String valueAsString = objectMapper.writeValueAsString(value);
            connection.set(key, valueAsString);
            connectionPool.put(connection);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            var connection = connectionPool.take();
            String valueAsString = connection.get(key);
            connectionPool.put(connection);
            return objectMapper.readValue(valueAsString, clazz);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(String key) {
        try {
            var connection = connectionPool.take();
            var result = connection.exists(key);
            connectionPool.put(connection);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
