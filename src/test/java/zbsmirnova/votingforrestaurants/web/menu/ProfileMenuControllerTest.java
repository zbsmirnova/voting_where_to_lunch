package zbsmirnova.votingforrestaurants.web.menu;

import org.junit.Test;
import org.springframework.http.MediaType;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.contentJson;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.KFC_ACTUAL_MENU;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1;
import static zbsmirnova.votingforrestaurants.util.MenuUtil.asTo;

public class ProfileMenuControllerTest extends AbstractControllerTest {
    private static final String URL = ProfileMenuController.URL + "/";

    @Test
    public void getTodayMenu() throws Exception{
        mockMvc.perform(get(URL, KFC_ID)
                .with(userHttpBasic(USER1))
                .param("restaurantId", String.valueOf(KFC_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(KFC_ACTUAL_MENU)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL, KFC_ID ))
                .andExpect(status().isUnauthorized());
    }

    ////    @Test
////    public void testGetNotFound() throws Exception {
////        mockMvc.perform(get(URL + 1)
////                .with(userHttpBasic(ADMIN)))
////                //.andExpect(status().isUnprocessableEntity())
////                .andExpect(status().isNotFound())
////                .andDo(print());
////    }
}