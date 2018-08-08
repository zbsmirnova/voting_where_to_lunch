package zbsmirnova.votingforrestaurants.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.service.MenuService;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;


public abstract class AbstractMenuController {

    @Autowired
    private MenuService service;

    private static final Logger log = LoggerFactory.getLogger(AbstractMenuController.class);

    public void delete(int id){
        //restaurant id ???
        log.info("delete menu {} ", id);
        service.delete(id);
    }

    Menu save(Menu menu, int restaurantId){
        log.info("create or update menu {} for restaurantId {}", menu, restaurantId);
        return service.save(menu, restaurantId);
    }

    Menu get(int id){
        log.info("get menu {}", id);
        //restaurant id ???
        return service.get(id);
    }

//    Menu getWithDishes(int id) throws NotFoundException;
//
//    List<Menu> getAll();
//
//    List<Menu> getAllByDate(LocalDate date);
//
//    List<Menu> getAll(int restaurantId);
//
//    Menu getByDateWithDishes(int restaurantId, LocalDate date) throws NotFoundException ;
//
//    Menu getTodayWithDishes(int restaurantId) throws NotFoundException ;
}
