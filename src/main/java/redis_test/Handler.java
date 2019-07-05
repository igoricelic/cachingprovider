package redis_test;

import javassist.util.proxy.MethodHandler;

import java.lang.reflect.Method;

public class Handler implements MethodHandler {

    @Override
    public Object invoke(Object o, Method thisMethod, Method proceed, Object[] objects) throws Throwable {
        System.out.println("Uhvacen!");
        return proceed.invoke(o, objects);
    }

}
