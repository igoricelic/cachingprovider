package core;

import core.factory.RegionManager;
import util.ConfigurationReader;
import util.impl.ConfigurationReaderImpl;

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
        ConfigurationReader configurationReader = new ConfigurationReaderImpl();
        var regions = configurationReader.readConfiguration(configurationFilePath);
        return of(regions);
    }

}
