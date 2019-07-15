package test.test1;

public class Test2 {

    public static void main(String[] args) {
        try {
            var instance = Service.class.newInstance();
            System.out.println(instance == null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
