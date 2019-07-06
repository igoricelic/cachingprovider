package test.test1;

import core.CachingManager;
import core.CachingRegistry;

import java.io.FileNotFoundException;

public class MainTest {

    public static void main(String[] args) {
        try {
            CachingManager manager = CachingRegistry.of("/Users/igoricelic/Documents/workspace/cachingprovider/src/main/java/test/test1/app.properties");
            Service service = manager.create(Service.class);

            System.out.println(service.addition(5, 6));
            System.out.println(service.addition(5, 6));
            System.out.println(service.multiplication(5, 6));
            System.out.println(service.multiplication(5, 6));
            System.out.println(service.subtraction(5, 6));
            System.out.println(service.subtraction(5, 6));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
