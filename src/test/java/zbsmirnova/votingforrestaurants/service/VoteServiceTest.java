package zbsmirnova.votingforrestaurants.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.testData.RestaurantTestData;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER2_ID;
import static zbsmirnova.votingforrestaurants.testData.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService service;

    @Test
    public void delete() {
        service.delete(VOTE_1_ID);
        assertMatch(service.getAll(), VOTE_2, VOTE_3);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(50);
    }

    @Test
    public void getAll(){
        assertMatch(service.getAll(), ALL_VOTES);
    }

    @Test
    public void get() {
        assertMatch(service.get(VOTE_1_ID), VOTE_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(50);
    }

    @Test
    public void create() {
        Vote created = getCreatedVote();
        service.save(created, USER1_ID, BUSHE_ID);
        assertMatch(service.getAll(), VOTE_1, VOTE_2, VOTE_3, created);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateUserIdDate(){
        service.save(getDuplicateUserIdDateVote(), USER1_ID, BUSHE_ID);
    }

    @Test
    public void update(){
        Vote updated = getUpdatedVote();
        service.save(updated, USER1_ID, MCDONALDS_ID);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    public void getAllByRestaurantId() {
        assertMatch(service.getAll(BUSHE_ID), VOTE_2);
    }

    @Test
    public void getAllByDate() {
        assertMatch(service.getAll(LocalDate.parse("2018-07-25")), VOTE_1, VOTE_2);
    }

    @Test
    public void getAllByUser() {
        assertMatch(service.getAllByUser(USER2_ID), VOTE_2, VOTE_3);
    }

    @Test
    public void getWithRestaurant() {
        Vote vote = service.getWithRestaurant(VOTE_1_ID);
        RestaurantTestData.assertMatch(vote.getRestaurant(), KFC);
    }
}