package zbsmirnova.votingforrestaurants.service;

import org.hibernate.boot.model.relational.NamedAuxiliaryDatabaseObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import zbsmirnova.votingforrestaurants.model.Role;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {
    @Autowired
    UserService service;

    @Test
    public void delete() {
        service.delete(USER1_ID);
        assertMatch(service.getAll(), ADMIN, USER2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(50);
    }

    @Test
    public void create() {
        User created = getCreatedUser();
        service.save(created);
        assertMatch(service.getAll(), ADMIN, USER1, USER2, created);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateEmail() throws Exception {
        service.save(getDuplicatedEmailUser());
    }

    @Test
    public void update() {
        User updated = getUpdatedUser();
        service.save(updated);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    public void get() {
        assertMatch(service.get(USER1_ID), USER1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(50);
    }

    @Test
    public void getAll() {
        List<User> users = service.getAll();
        assertMatch(service.getAll(), ALL_USERS);
    }

    @Test
    public void getWithVotes() {
        User user = service.getWithVotes(USER2_ID);

    }
}