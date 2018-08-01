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
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KETCHUP_ID;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC_ID;


public class MenuServiceTest extends AbstractServiceTest {
    @Autowired
    MenuService service;

    @Test
    public void delete() {
        service.delete(KFC_EXPIRED_MENU_ID);
        assertMatch(service.getAll(), MCDONALDS_EXPIRED_MENU, KETCHUP_EXPIRED_MENU, BUSHE_EXPIRED_MENU,
                KFC_ACTUAL_MENU, MCDONALDS_ACTUAL_MENU, KETCHUP_ACTUAL_MENU, BUSHE_ACTUAL_MENU);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(50);
    }

    @Test
    public void create() {
        Menu created = getCreatedMenu();
        service.save(created, KETCHUP_ID);
        assertMatch(service.getAll(), KFC_EXPIRED_MENU, MCDONALDS_EXPIRED_MENU, KETCHUP_EXPIRED_MENU, BUSHE_EXPIRED_MENU,
                KFC_ACTUAL_MENU, MCDONALDS_ACTUAL_MENU, KETCHUP_ACTUAL_MENU, BUSHE_ACTUAL_MENU, created);

    }

    @Test
    public void update(){
        Menu updated = getUpdatedMenu();
        service.save(updated, KETCHUP_ID);
        assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    public void getWithDishes() {
        Menu menu = service.getWithDishes(KETCHUP_EXPIRED_MENU_ID);
        DishTestData.assertMatch(menu.getDishes(), SALAD, KETCHUPBURGER, WATER);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), ALL_MENUS);
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
    public void getAllByRestaurantId(){
        List<Menu> kfcMenus = service.getAll(KFC_ID);
        assertMatch(service.getAll(KFC_ID), KFC_EXPIRED_MENU, KFC_ACTUAL_MENU);
    }

    @Test
    public void getByDateWithDishes(){
        Menu menu = service.getByDateWithDishes(KFC_ID, LocalDate.parse("2018-07-25"));
        assertMatch(menu, KFC_EXPIRED_MENU);
        DishTestData.assertMatch(menu.getDishes(),FRIES, CHICKEN, COLA);
    }

    @Test
    public void getTodayWithDishes(){
        Menu todayMenu = service.getByDateWithDishes(KFC_ID, LocalDate.now());
        assertMatch(todayMenu, KFC_ACTUAL_MENU);
        DishTestData.assertMatch(todayMenu.getDishes(), CHICKEN_SPECIAL);
    }

    @Test
    public void findAllByDate(){
        List<Menu> menus = service.getAllByDate(LocalDate.now());
        assertMatch(menus, KFC_ACTUAL_MENU, MCDONALDS_ACTUAL_MENU, KETCHUP_ACTUAL_MENU, BUSHE_ACTUAL_MENU);
    }
}