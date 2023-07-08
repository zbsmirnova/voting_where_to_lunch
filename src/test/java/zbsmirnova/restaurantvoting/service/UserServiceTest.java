package zbsmirnova.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import zbsmirnova.restaurantvoting.model.Role;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static zbsmirnova.restaurantvoting.TestUtil.NOT_EXISTING_ENTITY_ID;
import static zbsmirnova.restaurantvoting.testData.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {
    @Autowired
    UserService service;

    @Test
    public void get() {
        assertMatch(service.get(USER1_ID), USER1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTING_ENTITY_ID));
    }

    @Test
    public void getByEmail() {
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

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXISTING_ENTITY_ID));
    }

    @Test
    public void create() {
        User created = getCreatedUser();
        service.save(created);
        assertMatch(service.getAll(), ADMIN, USER1, USER2, created);
    }

    @Test
    public void createDuplicateEmail() {
        service.save(new User("UserDuplicated", USER1.getEmail(), "password", Role.USER));
        assertThrows(DataAccessException.class, () -> service.getAll());
    }

    @Test
    public void createInvalid() {
        service.save(new User(null, "email@test.ru", "123", Role.USER));
        assertThrows(ConstraintViolationException.class, () -> service.getAll());
    }

    @Test
    public void update() {
        User updated = getUpdatedUser();
        service.save(updated);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    public void updateInvalidId() {
        User updated = getUpdatedUser();
        updated.setId(NOT_EXISTING_ENTITY_ID);
        service.save(updated);
        assertThrows(DataIntegrityViolationException.class, () -> service.getAll());
    }
}