package zbsmirnova.votingforrestaurants.web.menu;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.service.MenuService;
import zbsmirnova.votingforrestaurants.to.MenuTo;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;
import zbsmirnova.votingforrestaurants.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.ADMIN;
import static zbsmirnova.votingforrestaurants.util.MenuUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.MenuUtil.createNewFromTo;


public class AdminMenuControllerTest extends AbstractControllerTest {
    private static final String URL = AdminMenuController.URL + '/';

    @Autowired
    MenuService service;

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + "{menuId}", KFC_ID, KFC_EXPIRED_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(KFC_ID), KFC_ACTUAL_MENU);
    }

//    @Test
//    public void testDeleteNotFound() throws Exception {
//        mockMvc.perform(delete(URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(URL,  BUSHE_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(BUSHE_EXPIRED_MENU), asTo(BUSHE_ACTUAL_MENU))));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + "{menuId}", BUSHE_ID, BUSHE_ACTUAL_MENU_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(BUSHE_ACTUAL_MENU)));
    }

    @Test
    public void testGetToday() throws Exception {
        mockMvc.perform(get(URL + "today", BUSHE_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(BUSHE_ACTUAL_MENU)));
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
    public void testCreate() throws Exception {

        ResultActions action = mockMvc.perform(post(URL,  MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN)))
                //.content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        Menu returned = TestUtil.readFromJson(action, Menu.class);
        Menu created = new Menu(MCDONALDS, LocalDate.now());
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(MCDONALDS_ID), MCDONALDS_ACTUAL_MENU, MCDONALDS_EXPIRED_MENU, created);
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
//    public void testCreateDuplicateDateRestaurant() throws Exception {
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
        MenuTo menuTo = new MenuTo();
        menuTo.setDate(LocalDate.now());
        menuTo.setRestaurantId(MCDONALDS_ID);
        mockMvc.perform(put(URL + MCDONALDS_ACTUAL_MENU_ID, MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(menuTo)))
                .andExpect(status().isOk());

        Menu updated = createNewFromTo(menuTo);
        updated.setId(MCDONALDS_ACTUAL_MENU_ID);
        assertMatch(service.get(MCDONALDS_ACTUAL_MENU_ID), updated);
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
}