package core.model;

import lombok.*;
import core.specification.KeyGenerator;
import core.specification.Provider;

/**
 * @author igoricelic
 */
@ToString
@Getter @Setter(value = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RegionManager {

    private String regionName;

    private KeyGenerator keyGenerator;

    private Provider provider;

    private int expirationTime;

    private boolean autoUpdate;

}
