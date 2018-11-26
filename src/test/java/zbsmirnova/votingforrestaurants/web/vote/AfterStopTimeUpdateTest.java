package zbsmirnova.votingforrestaurants.web.vote;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.MCDONALDS_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER2;
import static zbsmirnova.votingforrestaurants.util.ClockUtil.AFTER_STOP;

@ActiveProfiles(AFTER_STOP)
public class AfterStopTimeUpdateTest extends ProfileVoteControllerTest{

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(post(POST_URL,  MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2))
                .param("restaurantId", String.valueOf(MCDONALDS_ID)))
                .andExpect(status().isConflict());
    }

}
