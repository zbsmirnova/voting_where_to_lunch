package zbsmirnova.votingforrestaurants.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.to.UserTo;
import zbsmirnova.votingforrestaurants.util.UserUtil;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;
import zbsmirnova.votingforrestaurants.web.json.JsonUtil;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;
import static zbsmirnova.votingforrestaurants.util.UserUtil.asTo;
import static zbsmirnova.votingforrestaurants.web.user.ProfileController.PROFILE_URL;

public class ProfileControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception{
        TestUtil.print(
                mockMvc.perform(get(PROFILE_URL)
                        .with(userHttpBasic(USER1)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(asTo(USER1)))
        );
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(PROFILE_URL))
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
    public void testDelete() throws Exception{
        mockMvc.perform(delete(PROFILE_URL)
                .with(userHttpBasic(USER1)))
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

    @Test
    public void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        mockMvc.perform(put(PROFILE_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER1), updatedTo));
    }

//    @Test
//    public void testUpdateInvalid() throws Exception {
//        UserTo updatedTo = new UserTo(null, null, "password@pass.ru", "1234");
//
//        mockMvc.perform(put(PROFILE_URL).contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(USER1))
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andDo(print())
//                //.andExpect(status().isUnprocessableEntity())
//                //.andExpect(jsonPath("$.type").value("org.springframework.web.bind.MethodArgumentNotValidException"))
//                .andExpect(jsonPath("$.error").value("MethodArgumentNotValidException"))
//                .andDo(print());
//    }

//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    public void testDuplicate() throws Exception {
//        UserTo updatedTo = new UserTo(null, "duplicatedName", "admin@gmail.com", "password");
//
//        mockMvc.perform(put(PROFILE_URL).contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(USER1))
//                .content(JsonUtil.writeValue(updatedTo)))
//                .andExpect(status().isConflict())
////                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
////                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
//                .andDo(print());
//    }
}