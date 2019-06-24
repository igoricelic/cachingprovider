package core;

import exceptions.NonsupportCacheTypeException;
import implementation.basic.BasicKeyGenerator;
import implementation.basic.BasicProviderImpl;
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
    public static RegionManager getManager (String regionName, @NonNull CacheType cacheType) {
        switch (cacheType) {
            case ConcurrentHashMap:
                return new RegionManager(regionName, new BasicKeyGenerator(), new BasicProviderImpl());
            case Redis:
                return new RegionManager(regionName, new BasicKeyGenerator(), new RedisProviderImpl());
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
