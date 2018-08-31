package zbsmirnova.votingforrestaurants.web.dish;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.service.DishService;
import zbsmirnova.votingforrestaurants.testData.DishTestData;
import zbsmirnova.votingforrestaurants.testData.MenuTestData;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.DishTestData.*;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.BUSHE_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER2;
import static zbsmirnova.votingforrestaurants.util.DishUtil.asTo;


public class ProfileDishControllerTest extends AbstractControllerTest {
    @Autowired
    DishService service;

    private static final String URL = ProfileDishController.URL + '/';

    @Test
    public void testGet() throws Exception{
        mockMvc.perform(get(URL + CHICKEN_ID, KFC_ID, KFC_EXPIRED_MENU_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(CHICKEN)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL + CHICKEN_ID, KFC_ID, KFC_EXPIRED_MENU_ID))
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


    @Test
    public void testGetAllForMenuId() throws Exception{
        TestUtil.print(mockMvc.perform(get(URL, BUSHE_ID, BUSHE_EXPIRED_MENU_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJson(asTo(CAKE), asTo(BREAD), asTo(COFFEE))));

    }
}