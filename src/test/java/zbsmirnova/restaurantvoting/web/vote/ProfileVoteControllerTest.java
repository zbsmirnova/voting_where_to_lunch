package zbsmirnova.restaurantvoting.web.vote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.restaurantvoting.TestUtil;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.service.VoteService;
import zbsmirnova.restaurantvoting.to.VoteTo;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalDate;
import zbsmirnova.restaurantvoting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.KFC;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.MCDONALDS;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.MCDONALDS_ID;
import static zbsmirnova.restaurantvoting.testData.UserTestData.*;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.*;
import static zbsmirnova.restaurantvoting.util.VoteUtil.asTo;
import static zbsmirnova.restaurantvoting.web.vote.ProfileVoteController.GET_URL;


public class ProfileVoteControllerTest extends AbstractControllerTest{
    protected static final String POST_URL = ProfileVoteController.POST_URL + '/';

    @Autowired
    VoteService service;

    @Test
    public void testGet() throws Exception{
        TestUtil.print(
                mockMvc.perform(get(GET_URL)
                        .with(userHttpBasic(USER2)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(asTo(VOTE_3)))
        );
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(GET_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreate() throws Exception{
      ResultActions action = mockMvc.perform(post( POST_URL, MCDONALDS_ID)
          .contentType(MediaType.APPLICATION_JSON)
          .with(userHttpBasic(USER1)))
          .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        Vote created = new Vote(LocalDate.now(), USER1, MCDONALDS);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getTodayByUserId(USER1_ID), created);

    }}
