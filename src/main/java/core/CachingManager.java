package core;

import util.ConfigurationReader;

import java.util.List;

class CachingManager {

    static RegionProvider regionProvider;

    static ConfigurationReader configurationReader = null;

    static void setRegions(List<RegionManager> regions) {
        regionProvider = new RegionProvider(regions);
    }

    static void setRegions(String configFile) {
        setRegions(configurationReader.readConfiguration(configFile));
    }

    static <T> T newInstance (Class<T> clazz) {
        return null;
    }

}
