package zbsmirnova.votingforrestaurants.web.dish;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.service.DishService;
import zbsmirnova.votingforrestaurants.to.DishTo;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;
import zbsmirnova.votingforrestaurants.web.json.JsonUtil;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.DishTestData.*;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.BUSHE_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KETCHUP_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.ADMIN;
import static zbsmirnova.votingforrestaurants.util.DishUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.DishUtil.createNewFromTo;

public class AdminDishControllerTest extends AbstractControllerTest {
    private static final String URL = AdminDishController.URL + '/';

    @Autowired
    DishService service;

    @Test
    public void testGet() throws Exception{
        mockMvc.perform(get(URL + COFFEE_ID, BUSHE_ID, BUSHE_EXPIRED_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(COFFEE)));
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
    public void testGetAll() throws Exception{
        TestUtil.print(mockMvc.perform(get(URL, KETCHUP_ID, KETCHUP_EXPIRED_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(KETCHUPBURGER), asTo(SALAD), asTo(WATER))));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + CHICKEN_ID, KFC_ID, KFC_EXPIRED_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(KFC_EXPIRED_MENU_ID), FRIES, COLA);
    }

//    @Test
//    public void testDeleteNotFound() throws Exception {
//        mockMvc.perform(delete(URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }

    @Test
    public void testCreate() throws Exception {
        DishTo createdTo = new DishTo();
        createdTo.setName("newDish");
        createdTo.setPrice(12000);
        //createdTo.setMenuId(BUSHE_ACTUAL_MENU_ID);

        ResultActions action = mockMvc.perform(post(URL, BUSHE_ID, BUSHE_ACTUAL_MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        Dish created = createNewFromTo(createdTo);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(BUSHE_ACTUAL_MENU_ID), CAKE_SPECIAL, created);
    }

//    @Test
//    public void testCreateInvalid() throws Exception {
//        Restaurant created = new Restaurant(null);
//        ResultActions action = mockMvc.perform(post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(JsonUtil.writeValue(created)))
//                .andExpect(status().isUnprocessableEntity())
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
//                .andDo(print());
//    }

//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    public void testCreateDuplicate() throws Exception {
//        Restaurant invalid = new Restaurant("kfc");
//        mockMvc.perform(post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(invalid))
//                .with(userHttpBasic(ADMIN)))
//                .andDo(print())
//                .andExpect(status().isConflict());
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
////                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_DATETIME));
//    }

    @Test
    public void testUpdate() throws Exception {
        DishTo createdTo = new DishTo();
        createdTo.setName("updatedDish");
        createdTo.setPrice(12000);
        mockMvc.perform(put(URL + CAKE_ID, BUSHE_ID, BUSHE_EXPIRED_MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isOk());

        Dish updated = createNewFromTo(createdTo);
        updated.setId(CAKE_ID);
        assertMatch(service.get(CAKE_ID), updated);
    }

//    @Test
//    public void testUpdateInvalid() throws Exception {
//        Restaurant updated = new Restaurant();
//        updated.setName("");
//        updated.setId(KFC_ID);
//        mockMvc.perform(put(URL + KFC_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(JsonUtil.writeValue(updated)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
////                .andDo(print());
//    }

//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    public void testUpdateDuplicate() throws Exception {
//        User updated = new User(USER1);
//        updated.setEmail("admin@gmail.com");
//        mockMvc.perform(put(URL + USER1_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(jsonWithPassword(updated, "password")))
//                .andExpect(status().isConflict())
//                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
//                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
//                .andDo(print());
//    }
}