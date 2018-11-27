package zbsmirnova.restaurantvoting.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.getContent;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.testData.UserTestData.USER1;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeValue;


public class UserRestaurantControllerTest extends AbstractControllerTest {

    private static final String URL = ProfileRestaurantController.URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + KFC_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(KFC)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL + KFC_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + 1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAllWithTodayMenu() throws Exception {
        ResultActions action = mockMvc.perform(get(URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getAllRestaurantToWithTodayMenu()));

        String returned = getContent(action);
        String testData = writeValue(getAllRestaurantToWithTodayMenu());
        assertMatch(returned, testData);
    }
}