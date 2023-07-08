package zbsmirnova.restaurantvoting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.util.exception.InvalidVoteTimeException;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static zbsmirnova.restaurantvoting.TestUtil.NOT_EXISTING_ENTITY_ID;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.testData.UserTestData.*;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.assertMatch;
import static zbsmirnova.restaurantvoting.testData.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {
    private final LocalDateTime allowedTime = LocalDateTime.of(2023, 8, 23, 10, 30);
    private final LocalDateTime prohibitedTime = allowedTime.withHour(11);
    private final ZoneId zoneId = ZoneId.systemDefault();
    private final Clock allowedTimeClock = Clock.fixed(allowedTime.atZone(zoneId).toInstant(), zoneId);
    private final Clock prohibitedTimeClock = Clock.fixed(prohibitedTime.atZone(zoneId).toInstant(), zoneId);

    @Autowired
    VoteService service;

    @BeforeEach
    void setUp() {
        service.setClock(allowedTimeClock);
    }

    @Test
    public void get() {
        assertMatch(service.get(VOTE_1_ID), VOTE_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTING_ENTITY_ID));
    }

    @Test
    public void getTodayByUserId() {
        assertMatch(service.getTodayByUserId(USER2_ID), VOTE_3);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), ALL_VOTES);
    }

    @Test
    public void getByRestaurantIdAndDate() {
        assertMatch(service.getAll(BUSHE_ID, LocalDate.parse("2018-07-25")), VOTE_2);
    }

    @Test
    public void getAllToday() {
        assertMatch(service.getTodayVotes(), VOTE_3);
    }

    @Test
    public void delete() {
        service.delete(VOTE_1_ID);
        assertMatch(service.getAll(), VOTE_2, VOTE_3);
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXISTING_ENTITY_ID));
    }

    @Test
    public void create() {
        Vote created = service.create(USER1_ID, BUSHE_ID);
        assertMatch(service.getAll(), VOTE_1, VOTE_2, VOTE_3, created);
    }

    @Test
    void createInProhibitedTime() {
        service.setClock(prohibitedTimeClock);
        assertThrows(InvalidVoteTimeException.class, () -> service.create(USER1_ID, BUSHE_ID));
    }

    @Test
    public void createSecondVotePerUserPerDate() {
        service.create(USER1_ID, BUSHE_ID);
        assertThrows(IllegalArgumentException.class, () -> service.create(USER1_ID, MCDONALDS_ID));
    }

    @Test
    public void update() {
        service.update(VOTE_2_ID, MCDONALDS_ID);
        Vote updated = new Vote(VOTE_2_ID, LocalDate.now(), USER2, MCDONALDS);
        assertMatch(service.get(VOTE_2_ID), updated);
    }

    @Test
    void updateInProhibitedTime() {
        service.setClock(prohibitedTimeClock);
        assertThrows(InvalidVoteTimeException.class, () -> service.update(VOTE_1_ID, BUSHE_ID));
    }


    @Test
    public void updateInvalidId() {
        assertThrows(NotFoundException.class, () -> service.update(NOT_EXISTING_ENTITY_ID, MCDONALDS_ID));
    }
}