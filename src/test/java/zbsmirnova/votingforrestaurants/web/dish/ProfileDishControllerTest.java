package zbsmirnova.votingforrestaurants.web.dish;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import zbsmirnova.votingforrestaurants.service.DishService;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.DishTestData.CHICKEN;
import static zbsmirnova.votingforrestaurants.testData.DishTestData.CHICKEN_ID;
import static zbsmirnova.votingforrestaurants.testData.DishTestData.contentJson;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.KFC_EXPIRED_MENU;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1;
import static zbsmirnova.votingforrestaurants.util.DishUtil.asTo;


public class ProfileDishControllerTest extends AbstractControllerTest {
    @Autowired
    DishService service;

    private static final String URL = ProfileDishController.URL + '/';

    @Test
    public void testGet() throws Exception{
        mockMvc.perform(get(URL + CHICKEN_ID, KFC_ID, KFC_EXPIRED_MENU)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(CHICKEN)));
    }

    @Test
    public void getAllForMenu() {
    }
}