package test.test1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor @NoArgsConstructor @Data
public class SimpleObj implements Serializable {

    private int value;

    private String text;

    @Override
    public String toString() {
        return "SimpleObj{" +
                "value=" + value +
                ", text='" + text + '\'' +
                '}';
    }
}
