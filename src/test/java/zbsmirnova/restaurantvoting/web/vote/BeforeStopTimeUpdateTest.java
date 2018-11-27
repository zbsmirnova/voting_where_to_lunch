package zbsmirnova.restaurantvoting.web.vote;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import zbsmirnova.restaurantvoting.model.Vote;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.MCDONALDS;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.MCDONALDS_ID;
import static zbsmirnova.restaurantvoting.testData.UserTestData.USER2;
import static zbsmirnova.restaurantvoting.testData.UserTestData.USER2_ID;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.VOTE_3;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.assertMatch;
import static zbsmirnova.restaurantvoting.util.ClockUtil.BEFORE_STOP;

@ActiveProfiles(BEFORE_STOP)
public class BeforeStopTimeUpdateTest extends ProfileVoteControllerTest {

    @Test
    public void testUpdate() throws Exception {
            mockMvc.perform(post(POST_URL,  MCDONALDS_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(userHttpBasic(USER2))
                    .param("restaurantId", String.valueOf(MCDONALDS_ID)))
                    //.content(JsonUtil.writeValue(createdTo)))
                    .andExpect(status().isOk());

            Vote updated = new Vote(VOTE_3);
            updated.setRestaurant(MCDONALDS);
            assertMatch(service.getTodayByUserId(USER2_ID), updated);
        }

}
