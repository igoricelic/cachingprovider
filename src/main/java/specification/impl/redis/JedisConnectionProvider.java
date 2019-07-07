package specification.impl.redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

class JedisConnectionProvider {

    static BlockingQueue<Jedis> getPool(RedisConfig config) {
        BlockingQueue<Jedis> pool = new LinkedBlockingQueue<>();
        IntStream.range(0, config.getTotalConnections()).forEach(i -> pool.add(new Jedis(config.getHost(), config.getPort())));
        return pool;
    }

}
