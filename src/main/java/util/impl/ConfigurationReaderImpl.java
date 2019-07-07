package util.impl;

import core.CacheType;
import core.factory.RegionManager;
import core.factory.RegionManagerFactory;
import implementation.redis.RedisConfig;
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
        if(property.containsKey(RegionPropertyKey.default_config.toString()))
            if(Boolean.parseBoolean(property.get(RegionPropertyKey.default_config.toString()).toString()))
                return RegionManagerFactory.getManager(regionName, (CacheType) RegionPropertyKey.cache_type.defaultValue, new RedisConfig(),
                                (int) RegionPropertyKey.expiration_time.defaultValue, (boolean) RegionPropertyKey.auto_update.defaultValue);
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
        RedisConfig config = new RedisConfig();
        if(property.containsKey(RegionPropertyKey.redis_host.toString())) {
            config.setHost(property.get(RegionPropertyKey.redis_host.toString()).toString());
        }
        if(property.containsKey(RegionPropertyKey.redis_port.toString())) {
            try {
                config.setPort(Integer.parseInt(property.get(RegionPropertyKey.redis_port.toString()).toString()));
            } catch (NumberFormatException e) {
                // TODO: Exception
            }
        }
        if(property.containsKey(RegionPropertyKey.redis_connections.toString())) {
            try {
                config.setTotalConnections(Integer.parseInt(property.get(RegionPropertyKey.redis_connections.toString()).toString()));
            } catch (NumberFormatException e) {
                // TODO: Exception
            }
        }
        if(cacheType == CacheType.Custom) {
            var customRegion = RegionManagerFactory.getCustomManager(regionName);
            KeyGenerator keyGenerator = (KeyGenerator) RegionPropertyKey.key_generator.defaultValue;
            if(property.containsKey(RegionPropertyKey.key_generator.toString())) {
                try {
                    keyGenerator = (KeyGenerator) Class.forName(property
                            .get(RegionPropertyKey.key_generator.toString()).toString()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException
                        | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    // TODO: Exception
                }
            }
            Provider provider = (Provider) RegionPropertyKey.provider.defaultValue;
            if(property.containsKey(RegionPropertyKey.provider.toString())) {
                try {
                    provider = (Provider) Class.forName(property
                            .get(RegionPropertyKey.provider.toString()).toString()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException
                        | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    // TODO: Exception
                }
            }
            customRegion.setProvider(provider);
            customRegion.setKeyGenerator(keyGenerator);
            return customRegion;
        }
        return RegionManagerFactory.getManager(regionName, cacheType, config, expirationTime, autoUpdate);
    }

}
