package util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

    public static long getDiffInMinutes (LocalDateTime time1, LocalDateTime time2) {
        return ChronoUnit.MINUTES.between(time2, time1);
    }

}
