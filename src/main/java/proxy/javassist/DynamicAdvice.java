package proxy.javassist;

import core.model.annotations.Cacheable;
import core.model.RegionProvider;
import core.model.metadata.KeyMetadata;
import javassist.util.proxy.MethodHandler;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author igoricelic
 */
@AllArgsConstructor
public class DynamicAdvice implements MethodHandler {

    private RegionProvider regionProvider;

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        Optional<Cacheable> optionalCacheable = Optional.ofNullable(thisMethod.getAnnotation(Cacheable.class));
        if(optionalCacheable.isEmpty()) return proceed.invoke(self, args);
        Cacheable cacheable = optionalCacheable.get();
        var cacheResult = processing(self, cacheable.regions(), proceed, args, true, Optional.empty());
        if(cacheResult.isPresent()) return cacheResult.get();
        var result = proceed.invoke(self, args);
        processing(self, cacheable.regions(), proceed, args, false, Optional.of(result));
        return result;
    }

    private Optional<Object> processing(Object self, String[] regions, Method method, Object[] args, boolean stateCheck, Optional<Object> result) {
        for(String regionName: regions) {
            var provider = regionProvider.getProvider(regionName);
            var key = regionProvider.getKeyGenerator(regionName).generate(method, args);
            if(stateCheck) {
                if(provider.contains(key)) {
                    return Optional.ofNullable(provider.get(key, (Class<? extends Serializable>) method.getReturnType()));
                }
            } else {
                provider.metadata().put(key, new KeyMetadata(self, method, args, LocalDateTime.now()));
                provider.set(key, Serializable.class.cast(result.get()));
            }
        }
        return result;
    }

}
