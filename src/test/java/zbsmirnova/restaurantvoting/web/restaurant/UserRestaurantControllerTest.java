package zbsmirnova.restaurantvoting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zbsmirnova.restaurantvoting.service.RestaurantService;
import zbsmirnova.restaurantvoting.testData.DishTestData;
import zbsmirnova.restaurantvoting.to.RestaurantTo;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;

import java.util.List;

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

    private static final String URL_TEMPLATE = ProfileRestaurantController.URL + '/';

    @Autowired
    private RestaurantService service;

    @Test
    public void testGet() throws Exception {
        RestaurantTo kfcTo = asTo(KFC);
        kfcTo.setMenu(List.of(DishTestData.CHICKEN_SPECIAL));

        perform(MockMvcRequestBuilders.get(URL_TEMPLATE + KFC_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurantTos(kfcTo));
    }

    @Test
    public void testGetUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_TEMPLATE + KFC_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_TEMPLATE + 1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAllWithMenu() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(ProfileRestaurantController.URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurantTos(service.getAllWithMenu()));

        String returned = getContent(action);
        String testData = writeValue(service.getAllWithMenu());
        assertMatch(returned, testData);
    }
}