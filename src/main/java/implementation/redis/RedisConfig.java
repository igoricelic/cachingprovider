package implementation.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.RegionPropertyKey;

@AllArgsConstructor @NoArgsConstructor @Data
public class RedisConfig {

    private String host = RegionPropertyKey.redis_host.defaultValue.toString();

    private int port = (int) RegionPropertyKey.redis_port.defaultValue;

    private int totalConnections = (int) RegionPropertyKey.redis_connections.defaultValue;

}
