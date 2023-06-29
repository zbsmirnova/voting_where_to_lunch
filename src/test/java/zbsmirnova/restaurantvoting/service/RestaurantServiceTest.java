package zbsmirnova.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionSystemException;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.testData.DishTestData;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import static zbsmirnova.restaurantvoting.testData.DishTestData.KETCHUPBURGER_SPECIAL;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;

@DisplayName("Restaurant service tests")
@RequiredArgsConstructor
public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

//    @Autowired
//    private CacheManager cacheManager;

//    @Before
//    public void setUp() throws Exception {
//        Objects.requireNonNull(cacheManager.getCache("restaurantsWithTodayMenu")).clear();
//    }

    @Test
    public void get() {
        assertMatch(service.get(BUSHE_ID), BUSHE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound(){
        service.get(50);
    }

    @Test
    public void getWithTodayMenu(){
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

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(50);
    }

    @Test
    public void update() {
        Restaurant updated = getUpdatedRestaurant();
        service.update(asTo(updated), updated.getId());
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateInvalidId() {
        Restaurant updated = getUpdatedRestaurant();
        updated.setId(50);
        service.update(asTo(updated), updated.getId());
    }

    @Test
    public void create() {
        Restaurant created = getCreatedRestaurant();
        service.create(created);
        assertMatch(service.getAll(), created, BUSHE, KETCHUP, KFC, MCDONALDS);
    }

    @Test(expected = TransactionSystemException.class)
    public void createInvalid() {
        Restaurant created = new Restaurant(null, "");
        service.create(created);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateName(){
        service.create(new Restaurant("bushe", "addressBushe"));
    }
}