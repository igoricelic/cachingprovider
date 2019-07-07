package util;

import model.RegionManager;

import java.io.FileNotFoundException;
import java.util.List;

public interface ConfigurationReader {

    List<RegionManager> readConfiguration (String path) throws FileNotFoundException;

}
