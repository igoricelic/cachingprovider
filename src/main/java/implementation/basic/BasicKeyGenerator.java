package implementation.basic;

import specification.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 */
public final class BasicKeyGenerator implements KeyGenerator {

    private static final String KEY_NAME_DELIMITER = "-";

    /**
     * Format of key: hash_code_of_method:arg1-arg2-...-argn
     */
    public String generateKey(Method method, Object[] args) {
        var sufix = Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(KEY_NAME_DELIMITER));
        return String.format("%s:%s", System.identityHashCode(method), sufix);
    }

}
