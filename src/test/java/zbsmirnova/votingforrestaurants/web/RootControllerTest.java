package zbsmirnova.votingforrestaurants.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.votingforrestaurants.model.Role;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.testData.UserTestData;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;
import static zbsmirnova.votingforrestaurants.util.UserUtil.asTo;


public class RootControllerTest extends AbstractControllerTest{

    @Test
    public void registerNewUserAccount() throws Exception{
        User expected = new User(null, "newUserRegistered", "newUserRegistered1@mail.ru",
                "password",  Role.ROLE_USER);
        ResultActions action = mockMvc.perform(post( "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserTestData.jsonWithPassword(asTo(expected), "password")))
                //.content(UserTestData.jsonWithPassword(expected, "password")))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(),  ADMIN, USER1, USER2, expected);
    }
}