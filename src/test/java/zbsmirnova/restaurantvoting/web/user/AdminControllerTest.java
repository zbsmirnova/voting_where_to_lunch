package zbsmirnova.restaurantvoting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zbsmirnova.restaurantvoting.TestUtil;
import zbsmirnova.restaurantvoting.model.Role;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.service.UserService;
import zbsmirnova.restaurantvoting.testData.UserTestData;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;
import zbsmirnova.restaurantvoting.web.json.JsonUtil;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.UserTestData.*;
import static zbsmirnova.restaurantvoting.util.UserUtil.asTo;

public class AdminControllerTest extends AbstractControllerTest {

    private static final String URL_TEMPLATE = AdminController.URL + '/';

    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_TEMPLATE + ADMIN_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(ADMIN)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminController.URL))
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
        TestUtil.print(perform(MockMvcRequestBuilders.get(AdminController.URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(ADMIN), asTo(USER1), asTo(USER2))));
    }

    @Test
    public void testGetForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminController.URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDelete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + USER1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER2);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testCreateUser() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass",   Role.USER);
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(UserTestData.jsonWithPassword(asTo(expected), "newPass")))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER1, USER2);
    }

    @Test
    public void testCreateInvalid() throws Exception {
        User invalid = new User(null, "", "", "",   Role.USER);

        perform(MockMvcRequestBuilders.post(AdminController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

        @Test
    @Transactional//(propagation = Propagation.NEVER)
    public void testCreateDuplicate() throws Exception {
        User duplicate = new User(USER1);
            perform(MockMvcRequestBuilders.post(AdminController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().is5xxServerError());

    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setEmail("updated@mail.ru");
        updated.setPassword("updatedpassw");

        perform(MockMvcRequestBuilders.put(URL_TEMPLATE + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(asTo(updated), USER1.getPassword())))
                .andExpect(status().isOk());

        //assertMatch(userService.get(USER1_ID), updated);
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User updated = new User(USER1);
        updated.setName("");
        perform(MockMvcRequestBuilders.put(URL_TEMPLATE + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andDo(print());
    }
}