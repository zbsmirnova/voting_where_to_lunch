package zbsmirnova.votingforrestaurants.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static zbsmirnova.votingforrestaurants.testData.DishTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.BUSHE_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.MCDONALDS_ID;


public class DishServiceTest extends AbstractServiceTest{

    @Autowired
    protected DishService service;

    @Test
    public void get(){
        Dish actual = service.get(CHICKEN_ID, KFC_ID);
        assertMatch(actual, CHICKEN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundById(){
        Dish actual = service.get(100, BUSHE_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundByRestaurantId(){
        Dish actual = service.get(CHICKEN_ID, BUSHE_ID);
    }

    @Test
    public void getAll(){
        assertMatch(service.getAll(BUSHE_ID), BREAD, CAKE, COFFEE, CAKE_SPECIAL);
    }

    @Test
    public void getTodayMenu(){
        assertMatch(service.getTodayMenu(BUSHE_ID), CAKE_SPECIAL);
    }

    @Test
    public void getTodayMenuEmpty(){
        assertMatch(service.getTodayMenu(MCDONALDS_ID), Collections.emptyList());
    }

    @Test(expected = NotFoundException.class)
    public void getTodayMenuNotFoundByRestaurant(){
        assertMatch(service.getTodayMenu(100), CAKE_SPECIAL);
    }

    @Test
    public void delete(){
        service.delete(CHICKEN_ID, KFC_ID);
        assertMatch(service.getAll(KFC_ID), COLA, FRIES, CHICKEN_SPECIAL);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound(){
        service.delete(CHICKEN_ID, BUSHE_ID);
    }

    @Test
    public void create(){
        Dish created = getCreatedDish();
        service.save(created, KFC_ID);
        assertMatch(service.getAll(KFC_ID), CHICKEN, COLA, FRIES, created,  CHICKEN_SPECIAL);
    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void createInvalid(){
        Dish created = new Dish(20000, null, LocalDate.now());
        service.save(created, KFC_ID);
        assertMatch(service.getAll(KFC_ID), CHICKEN, FRIES, COLA, CHICKEN_SPECIAL, created);
    }

    @Test
    public void update(){
        Dish updated = getUpdatedDish();
        service.save(updated, KFC_ID);
        assertMatch(service.get(updated.getId(), KFC_ID), updated);

    }

    @Test(expected = NotFoundException.class)
    public void updateWithInvalidId(){
        Dish created = getCreatedDish();
        created.setId(50);
        service.save(created, BUSHE_ID);
    }
}
