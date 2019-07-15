package test.test1;

import core.model.CachingManager;
import core.factory.CachingRegistry;

import java.lang.reflect.Modifier;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        try {
            CachingManager manager = CachingRegistry.of("/Users/igoricelic/Documents/workspace/cachingprovider/src/main/java/test/test1/app.properties");
            Service service = manager.create(Service.class);

            System.out.println(Modifier.isFinal(Service.class.getModifiers()));
            System.out.println(Modifier.isAbstract(Service.class.getModifiers()));

            List<SimpleObj> objects1 = service.getSimpleObjects();
            objects1.stream().map(SimpleObj::toString).forEach(System.out::println);
            objects1.get(1).setValue(101);
            List<SimpleObj> objects2 = service.getSimpleObjects();
            objects2.stream().map(SimpleObj::toString).forEach(System.out::println);
            List<SimpleObj> objects3 = service.getSimpleObjects();
            objects3.stream().map(SimpleObj::toString).forEach(System.out::println);
            System.out.println(service.addition(5, 6));
            System.out.println(service.addition(5, 6));
            System.out.println(service.subtraction(5, 6));
            System.out.println(service.multiplication(6, 7));
            System.out.println(service.getDate());
            System.out.println(service.subtraction(5, 6));
            System.out.println(service.addition(5, 6));
            System.out.println(service.subtraction(5, 6));
            System.out.println(service.getDate());
            System.out.println(service.multiplication(6, 7));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
