package zbsmirnova.votingforrestaurants.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static zbsmirnova.votingforrestaurants.testData.DishTestData.*;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;


public class DishServiceTest extends AbstractServiceTest{

    @Autowired
    protected DishService service;

    @Test
    public void delete() throws Exception {
        service.delete(CHICKEN_ID);
        assertMatch(service.getAll(KFC_EXPIRED_MENU_ID), FRIES, COLA);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(KFC_EXPIRED_MENU_ID);
    }

    @Test
    public void create(){
        Dish created = getCreatedDish();
        service.save(created, KFC_EXPIRED_MENU_ID);
        assertMatch(service.getAll(KFC_EXPIRED_MENU_ID), CHICKEN, FRIES, COLA,  created);
    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void createInvalid(){
        Dish created = new Dish( 0, null);
        service.save(created, KFC_EXPIRED_MENU_ID);
        assertMatch(service.getAll(KFC_ID), CHICKEN, FRIES, COLA,  created);
    }

    @Test
    public void update(){
        Dish updated = getUpdatedDish();
        service.save(updated, KFC_EXPIRED_MENU_ID);
        assertMatch(service.get(updated.getId()), updated);

    }

    @Test(expected = NotFoundException.class)
    public void updateWithInvalidId(){
        Dish created = getCreatedDish();
        created.setId(50);
        service.save(created, KFC_EXPIRED_MENU_ID);
    }

    @Test
    public void get(){
        Dish actual = service.get(CHICKEN_ID);
        assertMatch(actual, CHICKEN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound(){
        Dish actual = service.get(KFC_EXPIRED_MENU_ID);
    }


    @Test
    public void getAllByMenuId(){
        assertMatch(service.getAll(BUSHE_EXPIRED_MENU_ID), CAKE, BREAD, COFFEE);
    }
}
