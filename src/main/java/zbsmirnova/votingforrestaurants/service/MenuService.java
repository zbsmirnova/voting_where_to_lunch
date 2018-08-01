package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {
    void delete(int id) throws NotFoundException;

    Menu save(Menu menu, int restaurantId);

    Menu get(int id) throws NotFoundException;

    Menu getWithDishes(int id) throws NotFoundException;

    List<Menu> getAll();

    List<Menu> getAllByDate(LocalDate date);

    List<Menu> getAll(int restaurantId);

    Menu getByDateWithDishes(int restaurantId, LocalDate date) throws NotFoundException ;

    Menu getTodayWithDishes(int restaurantId) throws NotFoundException ;
}
