package core.concurrency;

import core.specification.Provider;

public interface RegionWorkerProvider {

    void submit (boolean autoUpdate, int expirationTime, Provider provider);

    void shutdown ();

}
