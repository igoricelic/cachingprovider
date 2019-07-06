package core;

import core.factory.RegionManager;
import specification.KeyGenerator;
import specification.Provider;
import util.ConfigurationReader;
import util.impl.ConfigurationReaderImpl;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RegionProvider {

    private Map<String, RegionManager> regions;

    RegionProvider(List<RegionManager> listOfRegions) {
        this.regions = listOfRegions.stream().collect(Collectors.toMap(RegionManager::getRegionName, Function.identity()));
    }

    RegionProvider(String configFilePath) throws FileNotFoundException {
        ConfigurationReader configurationReader = new ConfigurationReaderImpl();
        this.regions = configurationReader.readConfiguration(configFilePath).stream()
                .collect(Collectors.toMap(RegionManager::getRegionName, Function.identity()));
    }


    public Provider getProvider (String regionName) {
        return regions.get(regionName).getProvider();
    }

    public KeyGenerator getKeyGenerator(String regionName) {
        return regions.get(regionName).getKeyGenerator();
    }

    public boolean existRegion (String regionName) {
        return regions.containsKey(regionName);
    }

}
