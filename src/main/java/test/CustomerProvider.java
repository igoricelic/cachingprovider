package test;

import specification.Provider;

public class CustomerProvider implements Provider {
    @Override
    public <T> void set(String key, T value) {

    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public boolean contains(String key) {
        return false;
    }
}
