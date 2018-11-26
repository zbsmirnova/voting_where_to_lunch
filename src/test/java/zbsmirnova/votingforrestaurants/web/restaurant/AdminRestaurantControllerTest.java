package zbsmirnova.votingforrestaurants.web.restaurant;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import static zbsmirnova.votingforrestaurants.TestUtil.getContent;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.getAllRestaurantToWithTodayMenu;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.createNewFromTo;
import static zbsmirnova.votingforrestaurants.web.json.JsonUtil.writeValue;


public class AdminRestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    RestaurantService service;

    private static final String URL = AdminRestaurantController.URL + '/';

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

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL + KFC_ID))
                .andExpect(status().isUnauthorized());
    }

        @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
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
    public void testGetAllWithTodayMenu() throws Exception {
        ResultActions action = mockMvc.perform(get(URL + "/getAllWithTodayMenu")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getAllRestaurantToWithTodayMenu()));

        String returned = getContent(action);
        String testData = writeValue(getAllRestaurantToWithTodayMenu());
        assertMatch(returned, testData);

    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + KFC_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), BUSHE, KETCHUP, MCDONALDS);
    }

        @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreate() throws Exception {
        RestaurantTo expected = new RestaurantTo("newRestaurant", "newAddress");
        ResultActions action = mockMvc.perform(post(URL)
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
                mockMvc.perform(post(URL)
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
        mockMvc.perform(post(URL)
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
        mockMvc.perform(put(URL + KFC_ID)
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
        mockMvc.perform(put(URL + KFC_ID)
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
        mockMvc.perform(put(URL + KFC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}