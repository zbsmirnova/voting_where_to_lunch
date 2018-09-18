package zbsmirnova.votingforrestaurants.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionSystemException;
import zbsmirnova.votingforrestaurants.model.Role;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import static zbsmirnova.votingforrestaurants.testData.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {
    @Autowired
    UserService service;

    @Test
    public void get() {
        assertMatch(service.get(USER1_ID), USER1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(50);
    }

    @Test
    public void getByEmail(){
        assertMatch(service.getByEmail("user1@yandex.ru"), USER1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), ALL_USERS);
    }

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

    @Test(expected = TransactionSystemException.class)
    public void createInvalid() {
        User created = new User(null, "email@test.ru", "123", Role.ROLE_USER);
        service.save(created);
    }

    @Test
    public void update() {
        User updated = getUpdatedUser();
        service.save(updated);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateInvalidId() {
        User updated = getUpdatedUser();
        updated.setId(50);
        service.save(updated);
    }
}