package zbsmirnova.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zbsmirnova.restaurantvoting.model.Dish;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static zbsmirnova.restaurantvoting.TestUtil.NOT_EXISTING_ENTITY_ID;
import static zbsmirnova.restaurantvoting.testData.DishTestData.assertMatch;
import static zbsmirnova.restaurantvoting.testData.DishTestData.*;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    protected DishService service;

    @Test
    public void get() {
        Dish actual = service.get(CHICKEN_ID, KFC_ID);
        assertMatch(actual, CHICKEN);
    }

    @Test
    public void getNotFoundById() {
        assertThrows(NotFoundException.class, () -> service.get(100, BUSHE_ID));
    }

    @Test
    public void getNotFoundByRestaurantId() {
        assertThrows(NotFoundException.class, () -> service.get(CHICKEN_ID, BUSHE_ID));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(BUSHE_ID), CAKE, BREAD, COFFEE, CAKE_SPECIAL);
    }

    @Test
    public void getTodayMenu() {
        assertMatch(service.getTodayMenu(BUSHE_ID), CAKE_SPECIAL);
    }

    @Test
    public void getTodayMenuEmpty() {
        assertMatch(service.getTodayMenu(MCDONALDS_ID), Collections.emptyList());
    }

    @Test
    public void getTodayMenuNotFoundByRestaurant() {
        assertThrows(NotFoundException.class, () -> service.getTodayMenu(100));
    }

    @Test
    public void delete() {
        service.delete(CHICKEN_ID, KFC_ID);
        assertMatch(service.getAll(KFC_ID), FRIES, COLA, CHICKEN_SPECIAL);
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(CHICKEN_ID, BUSHE_ID));
    }

    @Test
    public void create() {
        Dish created = getCreatedDish();
        service.create(created, KFC_ID);
        assertMatch(service.getAll(KFC_ID), CHICKEN, FRIES, COLA, CHICKEN_SPECIAL, created);
    }

    @Test
    public void createInvalid() {
        Dish created = new Dish(20000, null, LocalDate.now());
        service.create(created, KFC_ID);
        assertThrows(ConstraintViolationException.class, () -> service.getAll(KFC_ID));
    }

    @Test
    public void update() {
        Dish updated = getUpdatedDish();
        service.update(updated, KFC_ID);
        assertMatch(service.get(updated.getId(), KFC_ID), updated);

    }

    @Test
    public void updateWithInvalidId() {
        Dish created = getCreatedDish();
        created.setId(NOT_EXISTING_ENTITY_ID);
        assertThrows(NotFoundException.class, () -> service.update(created, BUSHE_ID));
    }
}
