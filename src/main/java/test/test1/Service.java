package test.test1;

import core.model.annotations.Cacheable;

import java.time.LocalDate;
import java.util.List;

public abstract class Service {

    public Service(int number) {
        System.out.println("Hey: "+number);
    }

    @Cacheable(regions = { "region1" })
    public Integer addition(int x, int y) {
        System.out.println("Pozvao");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x+y;
    }

    @Cacheable(regions = { "region2" })
    public Integer subtraction(int x, int y) {
        System.out.println("Pozvao");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x-y;
    }

    @Cacheable(regions = { "region2" })
    public Integer multiplication(int x, int y) {
        System.out.println("Pozvao");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x*y;
    }

    @Cacheable(regions = { "region1" })
    public List<SimpleObj> getSimpleObjects() {
        System.out.println("Pozvao");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return List.of(
                new SimpleObj(1, "Prvi"),
                new SimpleObj(2, "Drugi"),
                new SimpleObj(3, " Treci"),
                new SimpleObj(4, "Cetvrti"),
                new SimpleObj(5, "Peti")
        );
    }

    @Cacheable(regions = { "region1" })
    public LocalDate getDate() {
        System.out.println("Pozvao");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return LocalDate.now();
    }

}
