package redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor
@AllArgsConstructor @ToString
public class User {

    private Long id;

    private String firstName;

    private String lastName;

}
