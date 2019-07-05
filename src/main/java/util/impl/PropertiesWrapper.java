package util.impl;

import util.PropertiesIntegrationAdapter;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

final class PropertiesWrapper extends Properties implements PropertiesIntegrationAdapter {

    private static final String DOT = "\\.";

    @Override
    public Set<String> getAllRegions() {
        return keySet().stream().map(key -> key.toString().split(DOT)[0]).collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getRegionData(String region) {
        Set<String> keys = keySet().stream().filter(key -> key.toString().startsWith(region))
                .map(String::valueOf).collect(Collectors.toSet());
        return keys.stream().collect(Collectors.toMap(key -> key.split(DOT)[1], key -> get(key)));
    }
}
