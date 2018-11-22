package zbsmirnova.votingforrestaurants.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionSystemException;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.testData.DishTestData;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import static zbsmirnova.votingforrestaurants.testData.DishTestData.KETCHUPBURGER_SPECIAL;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.asTo;


public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }

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

//    @Test
//    public void getAll() {
//        assertMatch(service.getAllWithTodayMenu(), ALL_RESTAURANTS);
//    }
//
//    @Test
//    public void delete() {
//        service.delete(KFC_ID);
//        assertMatch(service.getAllWithTodayMenu(), BUSHE, KETCHUP, MCDONALDS);
//    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(50);
    }

    @Test
    public void update() {
        Restaurant updated = getUpdatedRestaurant();
        service.update(asTo(updated));
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateInvalidId() {
        Restaurant updated = getUpdatedRestaurant();
        updated.setId(50);
        service.update(asTo(updated));
    }

//    @Test
//    public void create() {
//        Restaurant created = getCreatedRestaurant();
//        service.create(created);
//        assertMatch(service.getAllWithTodayMenu(), created, BUSHE, KETCHUP, KFC, MCDONALDS);
//    }

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