package proxy;

import annotations.Cacheable;
import core.RegionProvider;
import javassist.util.proxy.MethodHandler;
import lombok.AllArgsConstructor;

import java.io.Serializable;
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
        var cacheResult = processing(cacheable.regions(), proceed, args, true, Optional.empty());
        if(cacheResult.isPresent()) return cacheResult.get();
        var result = proceed.invoke(self, args);
        processing(cacheable.regions(), proceed, args, false, Optional.of(result));
        return result;
    }

    private Optional<Object> processing(String[] regions, Method method, Object[] args, boolean stateCheck, Optional<Object> result) {
        for(String regionName: regions) {
            var provider = regionProvider.getProvider(regionName);
            var key = regionProvider.getKeyGenerator(regionName).generate(method, args);
            if(stateCheck && provider.contains(key)) {
                System.out.println("Pronadjen u regionu: "+regionName);
                return Optional.ofNullable(provider.get(key, (Class<? extends Serializable>) method.getReturnType()));
            } else {
                provider.set(key, Serializable.class.cast(result.get()));
            }
        }
        return result;
    }

}
