package proxy;

import annotations.Cacheable;
import core.RegionProvider;
import javassist.util.proxy.MethodHandler;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 *
 */
@AllArgsConstructor
public class DynamicAdvice implements MethodHandler {

    private RegionProvider regionProvider;

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        Optional<Cacheable> optionalCacheable = Optional.ofNullable(thisMethod.getAnnotation(Cacheable.class));
        if(optionalCacheable.isEmpty()) return proceed.invoke(self, args);
        Cacheable cacheable = optionalCacheable.get();
        for(String regionName: cacheable.regions()) {
            var provider = regionProvider.getProvider(regionName);
            var key = regionProvider.getKeyGenerator(regionName).generateKey(proceed, args);
            if(provider.contains(key)) return provider.get(key, proceed.getReturnType());
        }
        var result = proceed.invoke(self, args);
        for(String regionName: cacheable.regions()) {
            var provider = regionProvider.getProvider(regionName);
            var key = regionProvider.getKeyGenerator(regionName).generateKey(proceed, args);
            provider.set(key, result);
        }
        return result;
    }

//    private void processing(String[] regions, Object self, Method proceed, Object[] args) {
//        for(String regionName: regions) {
//            var provider = regionProvider.getProvider(regionName);
//            var key = regionProvider.getKeyGenerator(regionName).generateKey(proceed, args);
//        }
//    }

}
