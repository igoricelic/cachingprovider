package redis;

import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.InvocationTargetException;

public class JavaAssistTest {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(Service.class);
        Class<?> newClazz = proxyFactory.createClass();

        try {
            Service instance = Service.class.cast(newClazz.getDeclaredConstructor().newInstance());
            ((ProxyObject) instance).setHandler(new Handler());
            instance.call(4, 7);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
