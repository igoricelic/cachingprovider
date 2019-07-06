package core.factory;

import core.CacheType;
import exceptions.NonsupportCacheTypeException;
import implementation.basic.BasicKeyGenerator;
import implementation.basic.BasicProviderImpl;
import implementation.redis.RedisConfig;
import implementation.redis.RedisProviderImpl;
import lombok.NonNull;

/**
 *
 */
public class RegionManagerFactory {

    /**
     *
     * @param regionName
     * @param cacheType
     * @return
     */
    public static RegionManager getManager (String regionName, @NonNull CacheType cacheType, RedisConfig redisConfig, int expirationTime, boolean autoUpdate) {
        switch (cacheType) {
            case ConcurrentHashMap:
                return new RegionManager(regionName, new BasicKeyGenerator(), new BasicProviderImpl(), expirationTime, autoUpdate);
            case Redis:
                return new RegionManager(regionName, new BasicKeyGenerator(), new RedisProviderImpl(redisConfig), expirationTime, autoUpdate);
        }
        throw new NonsupportCacheTypeException("Cache type isn't support!");
    }

    /**
     *
     * @param regionName
     * @return
     */
    public static CustomRegionManager getCustomManager (String regionName) {
        return new CustomRegionManager(regionName);
    }


}
