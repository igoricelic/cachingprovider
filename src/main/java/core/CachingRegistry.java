package core;

import java.util.List;

/**
 *
 */
public class CachingRegistry {

    public static void setRegions(List<RegionManager> regions) {
        CachingManager.setRegions(regions);
    }

    public static void setRegions(String configFile) {
        CachingManager.setRegions(configFile);
    }

    public static <T> T newInstance (Class<T> clazz) {
        return CachingManager.newInstance(clazz);
    }

}
