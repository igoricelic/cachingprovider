package core.concurrency.impl;

import core.concurrency.RegionWorkerProvider;
import core.specification.Provider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegionWorkerProviderImpl implements RegionWorkerProvider {

    private static final int THREAD_POOL_SIZE = 20;

    private ExecutorService threadPool;

    public RegionWorkerProviderImpl() {
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Override
    public void submit(boolean autoUpdate, int expirationTime, Provider provider) {
        System.out.println("Dodaem novog workera");
        threadPool.submit(new RegionWorker(autoUpdate, expirationTime, provider));
    }

    @Override
    public void shutdown() {
        threadPool.shutdown();
    }

}
