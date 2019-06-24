package core.factory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import specification.KeyGenerator;
import specification.Provider;

/**
 *
 */
@Getter @Setter(value = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RegionManager {

    private String regionName;

    private KeyGenerator keyGenerator;

    private Provider provider;

    private int expirationTime;

    private boolean autoUpdate;

}
