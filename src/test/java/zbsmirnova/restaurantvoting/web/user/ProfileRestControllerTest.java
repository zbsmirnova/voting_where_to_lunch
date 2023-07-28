package zbsmirnova.restaurantvoting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zbsmirnova.restaurantvoting.TestUtil;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.service.UserService;
import zbsmirnova.restaurantvoting.to.UserTo;
import zbsmirnova.restaurantvoting.util.UserUtil;
import zbsmirnova.restaurantvoting.web.AbstractControllerTest;
import zbsmirnova.restaurantvoting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.restaurantvoting.TestUtil.userHttpBasic;
import static zbsmirnova.restaurantvoting.testData.UserTestData.*;
import static zbsmirnova.restaurantvoting.util.UserUtil.asTo;
import static zbsmirnova.restaurantvoting.web.user.ProfileRestController.PROFILE_URL;

public class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception{
        TestUtil.print(
                perform(MockMvcRequestBuilders.get(PROFILE_URL)
                        .with(userHttpBasic(USER1)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(asTo(USER1)))
        );
    }

    @Test
    public void testGetUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(PROFILE_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDelete() throws Exception{
        perform(MockMvcRequestBuilders.delete(PROFILE_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER2);
    }

    @Test
    public void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        perform(MockMvcRequestBuilders.put(PROFILE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER1), updatedTo));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password@pass.ru", "1234");

        perform(MockMvcRequestBuilders.put(PROFILE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "updated", USER2.getEmail(), "password");

        perform(MockMvcRequestBuilders.put(PROFILE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isConflict())
                .andDo(print());
    }
}