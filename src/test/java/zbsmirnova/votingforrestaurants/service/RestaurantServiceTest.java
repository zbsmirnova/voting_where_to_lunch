package zbsmirnova.votingforrestaurants.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.testData.MenuTestData;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import static zbsmirnova.votingforrestaurants.testData.MenuTestData.BUSHE_ACTUAL_MENU;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.BUSHE_EXPIRED_MENU;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;


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
        service.save(updated);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    public void create() {
        Restaurant created = getCreatedRestaurant();
        service.save(created);
        assertMatch(service.getAll(), created, BUSHE, KETCHUP, KFC, MCDONALDS);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateName() throws Exception {
        service.save(new Restaurant("bushe"));
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(BUSHE_ID);
        assertMatch(service.get(BUSHE_ID), BUSHE);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(50);
    }


    @Test
    public void getWithMenus() {
        Restaurant restaurant = service.getWithMenus(BUSHE_ID);
        MenuTestData.assertMatch(restaurant.getMenus(), BUSHE_EXPIRED_MENU, BUSHE_ACTUAL_MENU);
    }
}