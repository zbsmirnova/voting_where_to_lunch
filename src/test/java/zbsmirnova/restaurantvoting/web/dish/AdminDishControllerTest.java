package zbsmirnova.restaurantvoting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.restaurantvoting.TestUtil;
import zbsmirnova.restaurantvoting.model.Dish;
import zbsmirnova.restaurantvoting.service.DishService;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;
import zbsmirnova.restaurantvoting.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.DishTestData.assertMatch;
import static zbsmirnova.restaurantvoting.testData.DishTestData.contentJson;
import static zbsmirnova.restaurantvoting.testData.DishTestData.*;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.testData.UserTestData.ADMIN;

public class AdminDishControllerTest extends AbstractControllerTest {
    private static final String URL_TEMPLATE = AdminDishController.URL;

    @Autowired
    DishService service;

    @Test
    public void testGet() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_TEMPLATE + '/' + COFFEE_ID, BUSHE_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(COFFEE));
    }

    @Test
    public void testGetUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_TEMPLATE + '/' + CHICKEN_ID, KFC_ID))
                .andExpect(status().isUnauthorized());
    }

        @Test
    public void testGetNotFound() throws Exception {
            perform(MockMvcRequestBuilders.get(URL_TEMPLATE + '/' + 1, KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception{
        TestUtil.print(perform(MockMvcRequestBuilders.get(URL_TEMPLATE, KETCHUP_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(KETCHUPBURGER, SALAD, WATER, KETCHUPBURGER_SPECIAL)));
    }

    @Test
    public void testDelete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + '/' + CHICKEN_ID, KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(KFC_ID), FRIES, COLA, CHICKEN_SPECIAL);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + '/' + 1, KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreate() throws Exception {
        Dish created = new Dish(12000, "newDish", LocalDate.now());

        ResultActions action = perform(MockMvcRequestBuilders.post("/api/admin/restaurants/{restaurantId}/dishes", BUSHE_ID)//(URL_TEMPLATE, BUSHE_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getTodayMenu(BUSHE_ID), CAKE_SPECIAL, created);
    }

    @Test
    public void testCreateInvalid() throws Exception {
        Dish invalid = new Dish(0, "", LocalDate.now());

        perform(MockMvcRequestBuilders.post(URL_TEMPLATE, BUSHE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateDuplicate() throws Exception {
        Dish duplicate = new Dish(CHICKEN);
        duplicate.setId(null);
        duplicate.setRestaurant(KFC);
        perform(MockMvcRequestBuilders.post(URL_TEMPLATE, KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdate() throws Exception {
        Dish created = new Dish(CAKE);
        created.setPrice(3333);
        created.setName("updatedCake");

        perform(MockMvcRequestBuilders.put(URL_TEMPLATE + '/' + CAKE_ID, BUSHE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isOk());


        assertMatch(service.get(CAKE_ID, BUSHE_ID), created);
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Dish invalid = new Dish(CHICKEN);
        invalid.setName("");
        invalid.setId(KFC_ID);
        perform(MockMvcRequestBuilders.put(URL_TEMPLATE + '/' + CHICKEN_ID, KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}