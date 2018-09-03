package zbsmirnova.votingforrestaurants.web.vote;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static zbsmirnova.votingforrestaurants.util.ClockUtil.BEFORE_STOP;

@ActiveProfiles(BEFORE_STOP)
public class BeforeStopTimeUpdateTest extends ProfileVoteControllerTest {

    @Test
    public void testUpdate() throws Exception {
        super.testUpdate();
    }
}
