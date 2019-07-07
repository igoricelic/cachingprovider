package core;

import annotations.Cacheable;
import core.factory.RegionManager;
import specification.KeyGenerator;
import specification.Provider;
import util.ConfigurationReader;
import util.impl.ConfigurationReaderImpl;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RegionProvider {

    private Map<String, RegionManager> regions;

    RegionProvider(List<RegionManager> listOfRegions) {
        this.regions = listOfRegions.stream().collect(Collectors.toMap(RegionManager::getRegionName, Function.identity()));
    }

    public Provider getProvider (String regionName) {
        return regions.get(regionName).getProvider();
    }

    public KeyGenerator getKeyGenerator(String regionName) {
        return regions.get(regionName).getKeyGenerator();
    }

    public boolean validate(Class<?> clazz) {
        for(Method method: clazz.getDeclaredMethods()) {
            Optional<Cacheable> optionalCacheable = Optional.ofNullable(method.getAnnotation(Cacheable.class));
            if(optionalCacheable.isPresent()) {
                for(String regionName: optionalCacheable.get().regions()) {
                    if(!regions.containsKey(regionName)) {
                        // TODO: Exception
                    }
                }
            }
        }
        return true;
    }

}
