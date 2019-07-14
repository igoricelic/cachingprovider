package core.model;

import core.model.annotations.Cacheable;
import core.specification.KeyGenerator;
import core.specification.Provider;
import util.impl.Exceptions;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author igoricelic
 */
public class RegionProvider {

    private Map<String, RegionManager> regions;

    public RegionProvider(List<RegionManager> listOfRegions) {
        this.regions = listOfRegions.stream().collect(Collectors.toMap(RegionManager::getRegionName, Function.identity()));
    }

    public Provider getProvider (String regionName) {
        return regions.get(regionName).getProvider();
    }

    public KeyGenerator getKeyGenerator(String regionName) {
        return regions.get(regionName).getKeyGenerator();
    }

    void validate(Class<?> clazz) {
        for(Method method: clazz.getDeclaredMethods()) {
            Optional<Cacheable> optionalCacheable = Optional.ofNullable(method.getAnnotation(Cacheable.class));
            if(optionalCacheable.isPresent()) {
                for(String regionName: optionalCacheable.get().regions()) {
                    if(!regions.containsKey(regionName)) {
                        throw new Exceptions.InvalidRegionException(String.format("Invalid region! %s not found in region registry!", regionName));
                    }
                }
            }
        }
    }

}
