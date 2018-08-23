package zbsmirnova.votingforrestaurants.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.testData.DishTestData;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.votingforrestaurants.testData.DishTestData.*;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.BUSHE_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KETCHUP_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;


public class MenuServiceTest extends AbstractServiceTest {
    @Autowired
    MenuService service;

    @Test
    public void delete() {
        service.delete(KFC_EXPIRED_MENU_ID);
        assertMatch(service.getAll(KFC_ID), KFC_ACTUAL_MENU);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(50);
    }

    @Test
    public void create() {
        Menu created = getCreatedMenu();
        service.save(created, KETCHUP_ID);
        assertMatch(service.getAll(KETCHUP_ID), KETCHUP_EXPIRED_MENU, created, KETCHUP_ACTUAL_MENU);
    }

    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void createInvalid() {
        Menu created = new Menu(null);
        service.save(created, KETCHUP_ID);
    }

    @Test
    public void update(){
        Menu updated = getUpdatedMenu();
        service.save(updated, KETCHUP_ID);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateInvalidId(){
        Menu updated = getUpdatedMenu();
        updated.setId(50);
        service.save(updated, KETCHUP_ID);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(BUSHE_ID), BUSHE_EXPIRED_MENU, BUSHE_ACTUAL_MENU);
    }

    @Test
    public void getAllNotFound() {
        service.getAll(50);
    }

    @Test
    public void get() {
        assertMatch(service.get(KFC_EXPIRED_MENU_ID), KFC_EXPIRED_MENU);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(50);
    }

    @Test
    public void getToday(){
        assertMatch(service.getToday(BUSHE_ID), BUSHE_ACTUAL_MENU);
    }

    @Test(expected = NotFoundException.class)
    public void getTodayNotFound(){
        service.getToday(50);
    }
}