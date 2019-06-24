package specification;

import java.lang.reflect.Method;

/**
 *
 */
public interface KeyGenerator {

    String generateKey (Method method, Object[] args);

}
