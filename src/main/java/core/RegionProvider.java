package core;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RegionProvider {

    private Map<String, RegionManager> regions;

    RegionProvider(List<RegionManager> listOfRegions) {
        this.regions = listOfRegions.stream().collect(Collectors.toMap(RegionManager::getRegionName, Function.identity()));
    }

    public RegionManager getRegion (String regionName) {
        return regions.get(regionName);
    }

    public boolean existRegion (String regionName) {
        return regions.containsKey(regionName);
    }

}