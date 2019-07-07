package model;

import lombok.*;
import specification.KeyGenerator;
import specification.Provider;

/**
 *
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
