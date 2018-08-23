package zbsmirnova.votingforrestaurants.web.vote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.service.VoteService;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;
import zbsmirnova.votingforrestaurants.web.json.JsonUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.MCDONALDS;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.MCDONALDS_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;
import static zbsmirnova.votingforrestaurants.testData.VoteTestData.VOTE_3;
import static zbsmirnova.votingforrestaurants.testData.VoteTestData.assertMatch;


public class ProfileVoteControllerTest extends AbstractControllerTest {
    private static final String URL = ProfileVoteController.URL + '/';

    @Autowired
    VoteService service;

    @Test
    public void testCreate() throws Exception{
        ResultActions action = mockMvc.perform(post(URL,  MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .param("restaurantId", String.valueOf(MCDONALDS_ID)))
                //.content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        Vote created = new Vote(LocalDate.now(), USER1, MCDONALDS);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getTodayByUserId(USER1_ID), created);

    }

    //Doesn't work after 11.00 am
    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(post(URL,  MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2))
                .param("restaurantId", String.valueOf(MCDONALDS_ID)))
                //.content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        Vote updated = new Vote(VOTE_3);
        updated.setRestaurant(MCDONALDS);
        assertMatch(service.getTodayByUserId(USER2_ID), updated);

    }

    @Test
    public void testGet() {
    }
}