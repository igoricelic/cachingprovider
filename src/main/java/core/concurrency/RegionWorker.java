package core.concurrency;

import core.model.metadata.KeyMetadata;
import core.specification.Provider;
import lombok.AllArgsConstructor;
import util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
public class RegionWorker implements Runnable {

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
               provider.clear(metadataEntry.getKey());
               if (autoUpdateValues) {
                   
               }
           }
       }
    }

}
