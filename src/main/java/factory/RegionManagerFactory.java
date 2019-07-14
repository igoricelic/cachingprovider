package factory;

import model.enums.CacheType;
import model.CustomRegionManager;
import model.RegionManager;
import specification.impl.basic.BasicKeyGenerator;
import specification.impl.basic.BasicProviderImpl;
import specification.impl.redis.RedisConfig;
import specification.impl.redis.RedisProviderImpl;
import lombok.NonNull;
import util.impl.Exceptions;

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
        throw new Exceptions.InvalidRegionException("Cache type isn't support!");
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
