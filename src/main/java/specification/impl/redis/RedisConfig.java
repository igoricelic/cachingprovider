package specification.impl.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.RegionProperties;

@AllArgsConstructor @NoArgsConstructor @Data
public class RedisConfig {

    private String host = RegionProperties.redis_host.defaultValue.toString();

    private int port = (int) RegionProperties.redis_port.defaultValue;

    private int totalConnections = (int) RegionProperties.redis_connections.defaultValue;

}
