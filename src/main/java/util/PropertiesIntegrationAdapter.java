package util;

import java.util.Map;
import java.util.Set;

public interface PropertiesIntegrationAdapter {

    /**
     * Read all regions from property file.
     */
    Set<String> getAllRegions ();

    /**
     *
     */
    Map<String, Object> getRegionData (String region);

}
