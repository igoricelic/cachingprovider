package specification;

import java.lang.reflect.Method;

/**
 *
 */
public interface KeyGenerator {

    String generate(Method method, Object[] args);

}
