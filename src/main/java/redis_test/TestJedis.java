package redis_test;

import redis.clients.jedis.Jedis;

import java.util.logging.Logger;

public class TestJedis {

    private Logger logger = Logger.getLogger("JedisLogger");
    private Jedis jedis;

    public TestJedis() {
        this.jedis = new Jedis();
    }

    public void write(String key, String value, int threadId) {
        logger.info(String.format("Thread %d Write: key %s value %s", threadId, key, value));
        this.jedis.set(key, value);
    }

    public String read(String key, int threadId) {
        logger.info(String.format("Thread %d Read: key %s", threadId, key));
        return this.jedis.get(key);
    }


}
