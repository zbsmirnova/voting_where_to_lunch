package zbsmirnova.restaurantvoting.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.LocalDate;

import static zbsmirnova.restaurantvoting.TestUtil.NOT_EXISTING_ENTITY_ID;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.testData.UserTestData.USER1_ID;
import static zbsmirnova.restaurantvoting.testData.UserTestData.USER2_ID;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService service;

    @Test
    public void get() {
        assertMatch(service.get(VOTE_1_ID), VOTE_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(NOT_EXISTING_ENTITY_ID);
    }

    @Test
    public void getTodayByUserId(){
        assertMatch(service.getTodayByUserId(USER2_ID), VOTE_3);
    }

    @Test
    public void getAll(){
        assertMatch(service.getAll(), ALL_VOTES);
    }

    @Test
    public void getByRestaurantIdAndDate() {
        assertMatch(service.getAll(BUSHE_ID, LocalDate.parse("2018-07-25")), VOTE_2);
    }

    @Test
    public void getAllToday(){
        assertMatch(service.getTodayVotes(), VOTE_3);
    }

    @Test
    public void delete() {
        service.delete(VOTE_1_ID);
        assertMatch(service.getAll(), VOTE_2, VOTE_3);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(NOT_EXISTING_ENTITY_ID);
    }

    @Test
    public void create() {
        Vote created = service.create(USER1_ID, BUSHE_ID);
        assertMatch(service.getAll(), VOTE_1, VOTE_2, VOTE_3, created);
    }

    //TODO test second vote per user per date, check update

    @Test(expected = DataAccessException.class)
    public void createDuplicateUserIdDate(){
        service.create(USER1_ID, BUSHE_ID);
    }

    @Test
    public void update(){
        //TODO: vote id instead of User_id
        service.update(USER1_ID, MCDONALDS_ID);
        //assertMatch(service.get(MCDONALDS_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateInvalidId(){
        service.update(NOT_EXISTING_ENTITY_ID, MCDONALDS_ID);
    }
}