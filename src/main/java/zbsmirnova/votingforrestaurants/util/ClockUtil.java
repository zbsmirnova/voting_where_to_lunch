package zbsmirnova.votingforrestaurants.util;

import java.time.*;

public class ClockUtil {

    private static final Clock CLOCK_BEFORE_STOP = Clock.fixed(Instant.parse(LocalDate.now() + "T10:00:00.00Z"), ZoneId.of("UTC"));

    private static final Clock CLOCK_AFTER_STOP = Clock.fixed(Instant.parse(LocalDate.now() + "T14:00:00.00Z"), ZoneId.of("UTC"));

    public static final String BEFORE_STOP = "beforeStopTime";

    public static final String AFTER_STOP = "afterStopTime";

    private ClockUtil(){}

    public static Clock getClockBeforeStop() {
        return CLOCK_BEFORE_STOP;
    }

    public static Clock getClockAfterStop() {
        return CLOCK_AFTER_STOP;
    }

}
