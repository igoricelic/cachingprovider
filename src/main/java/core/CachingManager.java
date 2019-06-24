package core;

import core.factory.CacheableObjectFactory;

class CachingManager implements CacheableObjectFactory {

    private final RegionProvider regionProvider;

    CachingManager (RegionProvider regionProvider) {
        this.regionProvider = regionProvider;
    }


    @Override
    public <T> T create(Class<T> clazz) {
        return null;
    }
}
