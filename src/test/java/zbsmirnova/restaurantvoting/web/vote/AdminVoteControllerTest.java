package zbsmirnova.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zbsmirnova.restaurantvoting.TestUtil;
import zbsmirnova.restaurantvoting.service.VoteService;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.contentJsonArray;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.UserTestData.ADMIN;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.*;
import static zbsmirnova.restaurantvoting.util.VoteUtil.asTo;

public class AdminVoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteService service;

    private static final String URL = AdminVoteController.URL + '/';

    @Test
    public void testGet() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + VOTE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(VOTE_1)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void getTodayVotes() throws Exception {
        TestUtil.print(perform(MockMvcRequestBuilders.get(URL + "/today")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArray(asTo(VOTE_3))));
    }

    @Test
    public void getAll() throws Exception {
        TestUtil.print(perform(MockMvcRequestBuilders.get(AdminVoteController.URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArray(asTo(VOTE_1), asTo(VOTE_2), asTo(VOTE_3))));
    }

    @Test
    public void testDelete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + VOTE_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), VOTE_2, VOTE_3);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}