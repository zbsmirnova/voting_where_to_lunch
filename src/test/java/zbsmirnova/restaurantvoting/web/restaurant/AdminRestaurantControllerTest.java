package zbsmirnova.restaurantvoting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.restaurantvoting.TestUtil;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.service.RestaurantService;
import zbsmirnova.restaurantvoting.testData.DishTestData;
import zbsmirnova.restaurantvoting.to.RestaurantTo;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;
import zbsmirnova.restaurantvoting.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.getContent;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.testData.UserTestData.ADMIN;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.createNewFromTo;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeValue;

public class AdminRestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    RestaurantService service;

    private static final String URL_TEMPLATE = AdminRestaurantController.URL + '/';

    @Test
    public void testGet() throws Exception {
        RestaurantTo kfcTo = asTo(KFC);
        kfcTo.setMenu(List.of(DishTestData.CHICKEN_SPECIAL));

        perform(MockMvcRequestBuilders.get(URL_TEMPLATE + KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
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
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        RestaurantTo kfcTo = asTo(KFC);
        kfcTo.setMenu(List.of(DishTestData.CHICKEN_SPECIAL));

        ResultActions action = perform(MockMvcRequestBuilders.get(AdminRestaurantController.URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurants(service.getAll()));

        String returned = getContent(action);
        String testData = writeValue(service.getAll());
        assertMatch(returned, testData);
    }

    @Test
    public void testGetAllWithMenu() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(URL_TEMPLATE + "getAllWithMenu")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonRestaurantTos(service.getAllWithMenu()));

        String returned = getContent(action);
        String testData = writeValue(service.getAllWithMenu());
        assertMatch(returned, testData);
    }

    @Test
    public void testDelete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), BUSHE, KETCHUP, MCDONALDS);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreate() throws Exception {
        RestaurantTo expected = new RestaurantTo("newRestaurant", "newAddress");
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminRestaurantController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(expected)))
                .andExpect(status().isCreated());

        RestaurantTo returned = TestUtil.readFromJson(action, RestaurantTo.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
    }

    @Test
    public void testCreateInvalid() throws Exception {
        RestaurantTo invalid = new RestaurantTo("", "");
        perform(MockMvcRequestBuilders.post(AdminRestaurantController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateDuplicate() throws Exception {
        Restaurant invalid = new Restaurant("kfc", "addressKfc");
        perform(MockMvcRequestBuilders.post(AdminRestaurantController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());

    }

    @Test
    public void testUpdate() throws Exception {
        RestaurantTo restaurantTo = new RestaurantTo(KFC);
        restaurantTo.setName("UpdatedName");
        perform(MockMvcRequestBuilders.put(URL_TEMPLATE + KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(restaurantTo)))
                .andExpect(status().isOk());

        Restaurant updated = createNewFromTo(restaurantTo);
        updated.setId(KFC_ID);
        assertMatch(service.get(KFC_ID), updated);
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        RestaurantTo invalid = new RestaurantTo(KFC);
        invalid.setName("");
        invalid.setId(KFC_ID);
        perform(MockMvcRequestBuilders.put(URL_TEMPLATE + KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testUpdateDuplicate() throws Exception {
        Restaurant updated = new Restaurant(KFC);
        updated.setName("bushe");
        updated.setAddress("addressBushe");
        perform(MockMvcRequestBuilders.put(URL_TEMPLATE + KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}