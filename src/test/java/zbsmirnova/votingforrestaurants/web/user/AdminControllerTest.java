package zbsmirnova.votingforrestaurants.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.model.Role;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.testData.UserTestData;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;
import zbsmirnova.votingforrestaurants.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;
import static zbsmirnova.votingforrestaurants.util.UserUtil.asTo;

public class AdminControllerTest extends AbstractControllerTest {

    private static final String URL = AdminController.URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(URL + ADMIN_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(ADMIN)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isUnauthorized());
    }

//    @Test
//    public void testGetNotFound() throws Exception {
//        mockMvc.perform(get(URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(ADMIN), asTo(USER1), asTo(USER2))));
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateUser() throws Exception {
        //не работает для создания админа
        //User expected = new User(null, "New", "new@gmail.com", "newPass",   Role.ROLE_ADMIN);
        User expected = new User(null, "New", "new@gmail.com", "newPass",   Role.ROLE_USER);
        ResultActions action = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(UserTestData.jsonWithPassword(asTo(expected), "newPass")))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER1, USER2);
    }

//    @Test
//    public void testCreateInvalid() throws Exception {
//        User expected = new User(null, null, "", "newPass", Role.ROLE_USER);
//        ResultActions action = mockMvc.perform(post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(JsonUtil.writeValue(expected)))
//                .andExpect(status().isUnprocessableEntity())
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
//                .andDo(print());
//    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setEmail("updated@mail.ru");
        updated.setPassword("updatedpassw");
        //updated.setRole(Role.ROLE_ADMIN);
        mockMvc.perform(put(URL + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(asTo(updated), USER1.getPassword())))
                .andExpect(status().isOk());

        assertMatch(userService.get(USER1_ID), updated);
    }

//    @Test
//    public void testUpdateInvalid() throws Exception {
//        User updated = new User(USER1);
//        updated.setName("");
//        mockMvc.perform(put(URL + USER1_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(JsonUtil.writeValue(updated)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print())
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
//                .andDo(print());
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

//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    public void testCreateDuplicate() throws Exception {
//        User expected = new User(null, "New", "user@yandex.ru", "newPass", Role.ROLE_ADMIN);
//        mockMvc.perform(post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(ADMIN))
//                .content(jsonWithPassword(expected, "newPass")))
//                .andExpect(status().isConflict());
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
////                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL));
//
//    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(URL + USER1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER2);
    }

//    @Test
//    public void testDeleteNotFound() throws Exception {
//        mockMvc.perform(delete(URL + 1)
//                .with(userHttpBasic(ADMIN)))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print());
//    }


}