package redis;

import core.RegionManagerFactory;
import org.codehaus.jackson.map.ObjectMapper;
import redis.clients.jedis.Jedis;

public class SimpleTest1 {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
//        jedis.set("k1", String.valueOf(5));
//        jedis.set("k2", "Zdravo");
//        System.out.println(jedis.get("k1"));
//        System.out.println(jedis.get("k2"));
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            User u1 = new User(1l, "Pera", "Peric");
            String jsonU1 = objectMapper.writeValueAsString(u1);
            System.out.println(jsonU1);
            User u2 = new User(2l, "Mika", "Mikic");
            String jsonU2 = objectMapper.writeValueAsString(u2);
            System.out.println(jsonU2);

            jedis.set("u1", jsonU1);
            jedis.set("u2", jsonU2);
//            new RegionManager("", null, null);
//            new ConcurrentHashMap<>()

            RegionManagerFactory.getManager("n", null);
            User r1 = objectMapper.readValue(jedis.get("u1"), User.class);
            System.out.println(r1.toString());
            User r2 = objectMapper.readValue(jedis.get("u2"), User.class);
            System.out.println(r2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
