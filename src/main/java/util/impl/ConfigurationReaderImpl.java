package util.impl;

import model.enums.CacheType;
import model.RegionManager;
import factory.RegionManagerFactory;
import specification.impl.redis.RedisConfig;
import specification.KeyGenerator;
import specification.Provider;
import util.ConfigurationReader;
import model.enums.RegionProperties;

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
        if(property.containsKey(RegionProperties.default_config.toString()))
            if(Boolean.parseBoolean(property.get(RegionProperties.default_config.toString()).toString()))
                return RegionManagerFactory.getManager(regionName, (CacheType) RegionProperties.cache_type.defaultValue, new RedisConfig(),
                                (int) RegionProperties.expiration_time.defaultValue, (boolean) RegionProperties.auto_update.defaultValue);
        CacheType cacheType = (CacheType) RegionProperties.cache_type.defaultValue;
        if(property.containsKey(RegionProperties.cache_type.toString())) {
            try {
                cacheType = CacheType.valueOf(property.get(RegionProperties.cache_type.toString()).toString());
            } catch (IllegalArgumentException e) {
                throw new Exceptions.InvalidPropertyParameterException("Inavalid format: Valid values are ConcurrentHashMap, Redis, Custom!");
            }
        }
        int expirationTime = (int) RegionProperties.expiration_time.defaultValue;
        if(property.containsKey(RegionProperties.expiration_time.toString())) {
            try {
                expirationTime = Integer.parseInt(property.get(RegionProperties.cache_type.toString()).toString());
            } catch (NumberFormatException e) {
                throw new Exceptions.InvalidPropertyParameterException("Invalid format: ExpirationTime must be number!");
            }
        }
        boolean autoUpdate = (boolean) RegionProperties.auto_update.defaultValue;
        if(property.containsKey(RegionProperties.auto_update.toString())) {
            autoUpdate = Boolean.parseBoolean(property.get(RegionProperties.auto_update.toString()).toString());
        }
        RedisConfig config = new RedisConfig();
        if(property.containsKey(RegionProperties.redis_host.toString())) {
            config.setHost(property.get(RegionProperties.redis_host.toString()).toString());
        }
        if(property.containsKey(RegionProperties.redis_port.toString())) {
            try {
                config.setPort(Integer.parseInt(property.get(RegionProperties.redis_port.toString()).toString()));
            } catch (NumberFormatException e) {
                throw new Exceptions.InvalidPropertyParameterException("Invalid format: Port must be number!");
            }
        }
        if(property.containsKey(RegionProperties.redis_connections.toString())) {
            try {
                config.setTotalConnections(Integer.parseInt(property.get(RegionProperties.redis_connections.toString()).toString()));
            } catch (NumberFormatException e) {
                throw new Exceptions.InvalidPropertyParameterException("Invalid format: redis_connections parameter must be number!");
            }
        }
        if(cacheType == CacheType.Custom) {
            var customRegion = RegionManagerFactory.getCustomManager(regionName);
            KeyGenerator keyGenerator = (KeyGenerator) RegionProperties.key_generator.defaultValue;
            if(property.containsKey(RegionProperties.key_generator.toString())) {
                try {
                    keyGenerator = (KeyGenerator) Class.forName(property
                            .get(RegionProperties.key_generator.toString()).toString()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException
                        | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                    throw new Exceptions.InvalidPropertyParameterException("Invalid generator: Class not found!");
                }
            }
            Provider provider = (Provider) RegionProperties.provider.defaultValue;
            if(property.containsKey(RegionProperties.provider.toString())) {
                try {
                    provider = (Provider) Class.forName(property
                            .get(RegionProperties.provider.toString()).toString()).getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException
                        | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new Exceptions.InvalidPropertyParameterException("Invalid provider: Class not found!");
                }
            }
            customRegion.setProvider(provider);
            customRegion.setKeyGenerator(keyGenerator);
            return customRegion;
        }
        return RegionManagerFactory.getManager(regionName, cacheType, config, expirationTime, autoUpdate);
    }

}
