package util.impl;

import core.factory.RegionManager;
import core.factory.RegionManagerFactory;
import util.ConfigurationReader;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class ConfigurationReaderImpl implements ConfigurationReader {

    @Override
    public List<RegionManager> readConfiguration(String path) throws FileNotFoundException {
        File file = new File(path);
        if(!file.exists()) throw new FileNotFoundException();
        try (InputStream input = new FileInputStream(file)) {

            PropertiesWrapper prop = new PropertiesWrapper();
            prop.load(input);

            Set<String> regionNames = prop.getAllRegions();
            regionNames.forEach(regionName -> getManagerByProperty(regionName, prop.getRegionData(regionName)));

//            return regionNames.stream().map(regionName -> getManagerByProperty(regionName, prop.getRegionData(regionName)))
//                    .collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private RegionManager getManagerByProperty (String regionName, Map<String, Object> property) {
        System.out.println("=======================");
        System.out.println(regionName);
        System.out.println(property.toString());
        System.out.println("=======================");
        return null;
    }

    public static void main(String[] args) {
        ConfigurationReaderImpl reader = new ConfigurationReaderImpl();
        try {
            reader.readConfiguration("/Users/igoricelic/Documents/workspace-spring-tool-suite-4-4.1.0.RELEASE/ott_repositories/cachingprovider/src/main/java/test/simple.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
