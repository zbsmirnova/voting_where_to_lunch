package zbsmirnova.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.testData.DishTestData;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static zbsmirnova.restaurantvoting.TestUtil.NOT_EXISTING_ENTITY_ID;
import static zbsmirnova.restaurantvoting.testData.DishTestData.KETCHUPBURGER_SPECIAL;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;

@RequiredArgsConstructor
public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

    @Test
    public void get() {
        assertMatch(service.get(BUSHE_ID), BUSHE);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTING_ENTITY_ID));
    }

    @Test
    public void getWithTodayMenu() {
        Restaurant ketchupWithTodayMenu = service.getWithTodayMenu(KETCHUP_ID);
        assertMatch(ketchupWithTodayMenu, KETCHUP);
        DishTestData.assertMatch(ketchupWithTodayMenu.getDishes(), KETCHUPBURGER_SPECIAL);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), ALL_RESTAURANTS);
    }

    @Test
    public void delete() {
        service.delete(KFC_ID);
        assertMatch(service.getAll(), BUSHE, KETCHUP, MCDONALDS);
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXISTING_ENTITY_ID));
    }

    @Test
    public void update() {
        Restaurant updated = getUpdatedRestaurant();
        service.update(asTo(updated), updated.getId());
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    public void updateInvalidId() {
        Restaurant updated = getUpdatedRestaurant();
        updated.setId(NOT_EXISTING_ENTITY_ID);
        assertThrows(NotFoundException.class, () -> service.update(asTo(updated), updated.getId()));
    }

    @Test
    public void create() {
        Restaurant created = getCreatedRestaurant();
        service.create(created);
        assertMatch(service.getAll(), created, BUSHE, KETCHUP, KFC, MCDONALDS);
    }

    @Test
    public void createInvalid() {
        Restaurant created = new Restaurant(null, "");
        service.create(created);
        assertThrows(ConstraintViolationException.class, () -> service.getAll());
    }

    @Test
    public void createDuplicateName() {
        service.create(new Restaurant("bushe", "addressBushe"));
        assertThrows(DataAccessException.class, () -> service.getAll());
    }
}