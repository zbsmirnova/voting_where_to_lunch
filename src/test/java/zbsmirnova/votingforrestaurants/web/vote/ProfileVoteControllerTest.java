package zbsmirnova.votingforrestaurants.web.vote;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import zbsmirnova.votingforrestaurants.TestUtil;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.service.VoteService;
import zbsmirnova.votingforrestaurants.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static zbsmirnova.votingforrestaurants.TestUtil.userHttpBasic;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.MCDONALDS;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.MCDONALDS_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;
import static zbsmirnova.votingforrestaurants.testData.VoteTestData.*;
import static zbsmirnova.votingforrestaurants.util.VoteUtil.asTo;
import static zbsmirnova.votingforrestaurants.web.vote.ProfileVoteController.GET_URL;


public class ProfileVoteControllerTest extends AbstractControllerTest{
    private static final String POST_URL = ProfileVoteController.POST_URL + '/';

    @Autowired
    VoteService service;

    @Test
    public void testGet() throws Exception{
        TestUtil.print(
                mockMvc.perform(get(GET_URL)
                        .with(userHttpBasic(USER2)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(asTo(VOTE_3)))
        );
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(GET_URL))
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
    public void testCreate() throws Exception{
        ResultActions action = mockMvc.perform(post(POST_URL,  MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .param("restaurantId", String.valueOf(MCDONALDS_ID)))
                //.content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        Vote returned = TestUtil.readFromJson(action, Vote.class);
        Vote created = new Vote(LocalDate.now(), USER1, MCDONALDS);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getTodayByUserId(USER1_ID), created);

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


    //takes current time
    //passes if current time is before 11.00
    //fails if current time is after 11.00
    //@Test
    public void testUpdate() throws Exception {
        mockMvc.perform(post(POST_URL,  MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2))
                .param("restaurantId", String.valueOf(MCDONALDS_ID)))
                //.content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isOk());

        Vote updated = new Vote(VOTE_3);
        updated.setRestaurant(MCDONALDS);
        assertMatch(service.getTodayByUserId(USER2_ID), updated);
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