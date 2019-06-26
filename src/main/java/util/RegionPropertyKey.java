package util;

public enum RegionPropertyKey {

    key_generator ("default"),

    provider ("default"),

    cache_type ("ConcurrentHashMap"),

    redis_host ("localhost"),

    redis_port ("6379"),

    expiration_time (""),

    auto_update ("false");

    public final String label;

    RegionPropertyKey(String label) {
        this.label = label;
    }

}
