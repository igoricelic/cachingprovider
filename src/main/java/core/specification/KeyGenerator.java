package core.specification;

import java.lang.reflect.Method;

/**
 * @author igoricelic
 */
public interface KeyGenerator {

    String generate(Method method, Object[] args);

}
