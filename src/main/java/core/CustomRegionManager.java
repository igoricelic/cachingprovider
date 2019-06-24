package core;

import implementation.basic.BasicKeyGenerator;
import implementation.basic.BasicProviderImpl;
import specification.KeyGenerator;
import specification.Provider;

/**
 *
 */
public final class CustomRegionManager extends RegionManager {

    CustomRegionManager(String regionName) {
        super(regionName, new BasicKeyGenerator(), new BasicProviderImpl());
    }

    @Override
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        super.setKeyGenerator(keyGenerator);
    }

    @Override
    public void setProvider(Provider provider) {
        super.setProvider(provider);
    }

}