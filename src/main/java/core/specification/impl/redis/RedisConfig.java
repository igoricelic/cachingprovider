package core.specification.impl.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import core.model.enums.RegionProperties;

/**
 * @author igoricelic
 */
@AllArgsConstructor @NoArgsConstructor @Data
public final class RedisConfig {

    private String host = RegionProperties.redis_host.defaultValue.toString();

    private int port = (int) RegionProperties.redis_port.defaultValue;

    private int totalConnections = (int) RegionProperties.redis_connections.defaultValue;

}
