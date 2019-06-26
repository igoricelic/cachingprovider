package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    public static void main(String[] args) {
        readProperties("/Users/igoricelic/Documents/workspace-spring-tool-suite-4-4.1.0.RELEASE/ott_repositories/cachingprovider/src/main/java/test/simple.properties");
    }

    static void readProperties(String path) {
        try (InputStream input = new FileInputStream(path)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);


            // get the property value and print it out
            System.out.println(prop.get("region.cache.type"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
