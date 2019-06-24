package util;

import core.RegionManager;

import java.util.List;

public interface ConfigurationReader {

    List<RegionManager> readConfiguration (String path);

}
