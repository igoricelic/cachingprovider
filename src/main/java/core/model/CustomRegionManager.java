package core.model;

import core.specification.impl.basic.BasicKeyGenerator;
import core.specification.impl.basic.BasicProviderImpl;
import core.specification.KeyGenerator;
import core.specification.Provider;

/**
 * @author igoricelic
 */
public final class CustomRegionManager extends RegionManager {

    public CustomRegionManager(String regionName) {
        super(regionName, new BasicKeyGenerator(), new BasicProviderImpl(), -1, false);
    }

    @Override
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        super.setKeyGenerator(keyGenerator);
    }

    @Override
    public void setProvider(Provider provider) {
        super.setProvider(provider);
    }

    @Override
    public void setExpirationTime(int expirationTime) { super.setExpirationTime(expirationTime); }

    @Override
    public void setAutoUpdate(boolean autoUpdate) { super.setAutoUpdate(autoUpdate); }
}