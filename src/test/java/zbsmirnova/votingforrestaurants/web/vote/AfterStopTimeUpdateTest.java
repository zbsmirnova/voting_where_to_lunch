package zbsmirnova.votingforrestaurants.web.vote;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import zbsmirnova.votingforrestaurants.util.exception.InvalidVoteTimeException;

import static zbsmirnova.votingforrestaurants.util.ClockUtil.AFTER_STOP;

@ActiveProfiles(AFTER_STOP)
public class AfterStopTimeUpdateTest extends ProfileVoteControllerTest{

    @Test(expected = InvalidVoteTimeException.class)
    public void testUpdate() throws Exception {
       super.testUpdate();
    }

}
