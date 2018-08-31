package zbsmirnova.votingforrestaurants.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ClockUtil {

    private static final Clock clock = Clock.fixed(Instant.parse(LocalDate.now() + "T10:00:00.00Z"), ZoneId.systemDefault());;

    private ClockUtil(){}

    public static Clock getClock() {
        return clock;
    }
}
