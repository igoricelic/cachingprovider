package util.impl;

import core.CacheType;
import core.factory.RegionManager;
import core.factory.RegionManagerFactory;
import specification.KeyGenerator;
import specification.Provider;
import util.ConfigurationReader;
import util.RegionPropertyKey;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
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

            return regionNames.stream().map(regionName -> getManagerByProperty(regionName, prop.getRegionData(regionName)))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private RegionManager getManagerByProperty (String regionName, Map<String, Object> property) {
        CacheType cacheType = (CacheType) RegionPropertyKey.cache_type.defaultValue;
        if(property.containsKey(RegionPropertyKey.cache_type.toString())) {
            try {
                cacheType = CacheType.valueOf(property.get(RegionPropertyKey.cache_type.toString()).toString());
            } catch (IllegalArgumentException e) {
                // TODO: Exception
            }
        }
        int expirationTime = (int) RegionPropertyKey.expiration_time.defaultValue;
        if(property.containsKey(RegionPropertyKey.cache_type.toString())) {
            try {
                expirationTime = Integer.parseInt(property.get(RegionPropertyKey.cache_type.toString()).toString());
            } catch (NumberFormatException e) {
                // TODO: Exception
            }
        }
        boolean autoUpdate = (boolean) RegionPropertyKey.auto_update.defaultValue;
        if(property.containsKey(RegionPropertyKey.auto_update.toString())) {
            autoUpdate = Boolean.parseBoolean(property.get(RegionPropertyKey.auto_update.toString()).toString());
        }
        if(cacheType == CacheType.Custom) {
            var customRegion = RegionManagerFactory.getCustomManager(regionName);
            KeyGenerator keyGenerator = (KeyGenerator) RegionPropertyKey.key_generator.defaultValue;
            if(property.containsKey(RegionPropertyKey.key_generator.toString())) {
                try {
                    keyGenerator = (KeyGenerator) Class.forName(property.get(RegionPropertyKey.key_generator.toString()).toString()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException
                        | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    // TODO: Exception
                }
            }
            Provider provider = (Provider) RegionPropertyKey.provider.defaultValue;
            if(property.containsKey(RegionPropertyKey.provider.toString())) {
                try {
                    provider = (Provider) Class.forName(property.get(RegionPropertyKey.provider.toString()).toString()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException
                        | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    // TODO: Exception
                }
            }
            customRegion.setProvider(provider);
            customRegion.setKeyGenerator(keyGenerator);
        }
        return RegionManagerFactory.getManager(regionName, cacheType, expirationTime, autoUpdate);
    }

}
