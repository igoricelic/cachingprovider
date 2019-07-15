package core.specification.impl.redis;

import core.specification.AbstractProvider;
import redis.clients.jedis.Jedis;
import core.specification.ObjectSerializeProvider;
import core.specification.impl.ByteSerialization;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

/**
 * @author igoricelic
 */
public final class RedisProviderImpl extends AbstractProvider {

    private final BlockingQueue<Jedis> connectionPool;

    private final ObjectSerializeProvider objectSerializeProvider;

    public RedisProviderImpl(RedisConfig config) {
        this.objectSerializeProvider = new ByteSerialization();
        this.connectionPool = JedisConnectionProvider.getPool(config);
    }

    @Override
    public <T extends Serializable> void set(String key, T value) {
        try {
            var connection = connectionPool.take();
            String valueAsString = objectSerializeProvider.toString(value);
            connection.set(key, valueAsString);
            connectionPool.put(connection);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends Serializable> T get(String key, Class<T> clazz) {
        try {
            var connection = connectionPool.take();
            String valueAsString = connection.get(key);
            connectionPool.put(connection);
            return objectSerializeProvider.toObject(valueAsString, clazz);
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

    @Override
    public boolean clear(String key) {
        try {
            var connection = connectionPool.take();
            connection.del(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.clear(key);
    }
}
