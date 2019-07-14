package core.model.enums;

import core.specification.impl.basic.BasicKeyGenerator;
import core.specification.impl.basic.BasicProviderImpl;

/**
 * @author igoricelic
 */
public enum RegionProperties {

    default_config (false),

    key_generator (new BasicKeyGenerator()),

    provider (new BasicProviderImpl()),

    cache_type (CacheType.ConcurrentHashMap),

    redis_host ("localhost"),

    redis_port (6379),

    redis_connections (5),

    expiration_time (0),

    auto_update (false);

    public final Object defaultValue;

    RegionProperties(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

}
