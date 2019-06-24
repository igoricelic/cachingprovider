package core;

import core.factory.RegionManager;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 */
public class CachingRegistry {

    public static CachingManager of (List<RegionManager> regions) {
        return new CachingManager(new RegionProvider(regions));
    }

    public static CachingManager of (String configurationFilePath) throws FileNotFoundException {
        return new CachingManager(new RegionProvider(configurationFilePath));
    }

}
