package zbsmirnova.votingforrestaurants.web.restaurant;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.service.RestaurantService;
import zbsmirnova.votingforrestaurants.to.RestaurantTo;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;
import zbsmirnova.votingforrestaurants.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.createNewFromTo;


public class AdminRestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    RestaurantService service;

    private static final String URL = AdminRestaurantController.URL + '/';

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL + KFC_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(ALL_RESTAURANTS))));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(KFC)));
    }

//    @Test
//    public void testGetNotFound() throws Exception {
//        mockMvc.perform(get(URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                //.andExpect(status().isUnprocessableEntity())
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }

    @Test
    public void testCreate() throws Exception {
        RestaurantTo createdTo = new RestaurantTo();
        createdTo.setName("newRestaurant");
        ResultActions action = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        Restaurant created = createNewFromTo(createdTo);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(), BUSHE, KETCHUP, KFC, MCDONALDS, created);
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
        RestaurantTo restaurantTo = new RestaurantTo();
        restaurantTo.setName("UpdatedName");
        mockMvc.perform(put(URL + KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(restaurantTo)))
                .andExpect(status().isOk());

        Restaurant updated = createNewFromTo(restaurantTo);
        updated.setId(KFC_ID);
        assertMatch(service.get(KFC_ID), updated);
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
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
//////                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
//                .andDo(print());
//    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), BUSHE, KETCHUP, MCDONALDS);
    }

//    @Test
//    public void testDeleteNotFound() throws Exception {
//        mockMvc.perform(delete(URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }
}