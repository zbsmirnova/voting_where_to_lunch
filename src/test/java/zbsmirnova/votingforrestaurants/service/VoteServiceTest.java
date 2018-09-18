package zbsmirnova.votingforrestaurants.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;

import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER1_ID;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.USER2_ID;
import static zbsmirnova.votingforrestaurants.testData.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService service;

    @Test
    public void get() {
        assertMatch(service.get(VOTE_1_ID), VOTE_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(50);
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
        service.delete(50);
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

    @Test(expected = NotFoundException.class)
    public void updateInvalidId(){
        Vote updated = getUpdatedVote();
        updated.setId(50);
        service.save(updated, USER1_ID, MCDONALDS_ID);
    }

}