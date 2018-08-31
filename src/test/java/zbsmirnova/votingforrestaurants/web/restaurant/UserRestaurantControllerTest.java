package zbsmirnova.votingforrestaurants.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.asTo;


public class UserRestaurantControllerTest extends AbstractControllerTest {

    private static final String URL = UserRestaurantController.URL + '/';

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

//    @Test
//    public void testGetNotFound() throws Exception {
//        mockMvc.perform(get(URL + 1)
//                .with(userHttpBasic(USER1)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(ALL_RESTAURANTS))));
    }
}