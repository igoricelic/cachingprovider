package test.test1;

import annotations.Cacheable;

import java.lang.reflect.Method;

public class Service {

    @Cacheable(regions = { "region1" })
    public Integer addition(int x, int y) {
        System.out.println("Pozvao");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x+y;
    }

    @Cacheable(regions = { "region1" })
    public Integer subtraction(int x, int y) {
        System.out.println("Pozvao");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x-y;
    }

    public Integer multiplication(int x, int y) {
        System.out.println("Pozvao");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x*y;
    }

}
