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

    public String generateKey(Method method, Object[] args) {
        return Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(KEY_NAME_DELIMITER));
    }

}
