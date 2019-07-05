package util;

import core.CacheType;
import implementation.basic.BasicKeyGenerator;
import implementation.basic.BasicProviderImpl;

public enum RegionPropertyKey {

    key_generator (new BasicKeyGenerator()),

    provider (new BasicProviderImpl()),

    cache_type (CacheType.ConcurrentHashMap),

    redis_host ("localhost"),

    redis_port ("6379"),

    expiration_time (0),

    auto_update (false);

    public final Object defaultValue;

    RegionPropertyKey(Object label) {
        this.defaultValue = label;
    }

}
