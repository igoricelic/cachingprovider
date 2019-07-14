package core.factory;

import core.model.enums.CacheType;
import core.model.CustomRegionManager;
import core.model.RegionManager;
import core.specification.impl.basic.BasicKeyGenerator;
import core.specification.impl.basic.BasicProviderImpl;
import core.specification.impl.redis.RedisConfig;
import core.specification.impl.redis.RedisProviderImpl;
import lombok.NonNull;
import util.impl.Exceptions;

/**
 * The role of this core.factory is to be alternative to the configuration files.
 * This is an easy way to form regions through java code.
 * @author igoricelic
 */
public class RegionManagerFactory {

    /**
     * Returns the instance of the region manager with the desired configuration.
     * The regained instance of the region is immutable, for customisation use CustomRegionManager
     */
    public static RegionManager getManager (String regionName, @NonNull CacheType cacheType, RedisConfig redisConfig, int expirationTime, boolean autoUpdate) {
        switch (cacheType) {
            case ConcurrentHashMap:
                return new RegionManager(regionName, new BasicKeyGenerator(), new BasicProviderImpl(), expirationTime, autoUpdate);
            case Redis:
                return new RegionManager(regionName, new BasicKeyGenerator(), new RedisProviderImpl(redisConfig), expirationTime, autoUpdate);
        }
        throw new Exceptions.InvalidRegionException("Cache type isn't support!");
    }

    /**
     * Instance of the cache region with the default configuration set and available set methods for customization.
     * actions: setProvider, setKeyGenerator
     */
    public static CustomRegionManager getCustomManager (String regionName) {
        return new CustomRegionManager(regionName);
    }


}
