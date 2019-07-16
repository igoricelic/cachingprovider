package core.concurrency.impl;

import core.model.metadata.KeyMetadata;
import core.specification.Provider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import util.DateTimeUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class RegionWorker implements Runnable {

    private static final long SLEEP_TIME = 1000;

    private final boolean autoUpdateValues;

    private final long expirationTimeInMinutes;

    private final Provider provider;

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(SLEEP_TIME);
                processRegionData();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void processRegionData () {
       for(Map.Entry<String, KeyMetadata> metadataEntry: provider.metadata().entrySet()) {
           var createdTime = metadataEntry.getValue().getCreatedTime();
           if((DateTimeUtil.getDiffInMinutes(createdTime, LocalDateTime.now()) % expirationTimeInMinutes) == 0) {
               if (autoUpdateValues) {
                   var metadata = metadataEntry.getValue();
                   try {
                       var result = metadata.getTargetMethod().invoke(metadata.getTargetObject(), metadata.getArgs());
                       provider.set(metadataEntry.getKey(), Serializable.class.cast(result));
                   } catch (IllegalAccessException | InvocationTargetException e) {
                       e.printStackTrace();
                   }
               } else {
                   provider.clear(metadataEntry.getKey());
               }
           }
       }
    }

}
