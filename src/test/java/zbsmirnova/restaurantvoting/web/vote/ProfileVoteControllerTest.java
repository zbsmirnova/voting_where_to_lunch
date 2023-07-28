package zbsmirnova.restaurantvoting.web.vote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zbsmirnova.restaurantvoting.TestUtil;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.service.VoteService;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.*;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.MCDONALDS;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.MCDONALDS_ID;
import static zbsmirnova.restaurantvoting.testData.UserTestData.*;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.assertMatch;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.contentJson;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.*;
import static zbsmirnova.restaurantvoting.util.VoteUtil.asTo;
import static zbsmirnova.restaurantvoting.web.vote.ProfileVoteController.*;

public class ProfileVoteControllerTest extends AbstractControllerTest {

    @Autowired
    VoteService service;

    @BeforeEach
    public void setUp() {
        service.setClock(ALLOWED_TIME_CLOCK);
    }

    @Test
    public void testGet() throws Exception {
        TestUtil.print(
                perform(MockMvcRequestBuilders.get(GET_URL)
                        .with(userHttpBasic(USER2)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(asTo(VOTE_3))));
    }

    @Test
    public void testGetUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(GET_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateInAllowedTime() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(POST_URL, MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        Vote created = new Vote(LocalDate.now(), USER1, MCDONALDS);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getTodayByUserId(USER1_ID), created);
    }

    @Test
    public void testCreateInProhibitedTime() throws Exception {
        service.setClock(PROHIBITED_TIME_CLOCK);

        perform(MockMvcRequestBuilders.post(PUT_URL, VOTE_3_ID, MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testUpdateInAllowedTime() throws Exception {
        perform(MockMvcRequestBuilders.put(PUT_URL, VOTE_3_ID, MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isNoContent());

        Vote updated = new Vote(VOTE_3);
        updated.setRestaurant(MCDONALDS);
        assertMatch(service.getTodayByUserId(USER2_ID), updated);
    }

    @Test
    public void testUpdateInProhibitedTime() throws Exception {
        service.setClock(PROHIBITED_TIME_CLOCK);

        perform(MockMvcRequestBuilders.put(PUT_URL, VOTE_3_ID, MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isConflict());
    }
}