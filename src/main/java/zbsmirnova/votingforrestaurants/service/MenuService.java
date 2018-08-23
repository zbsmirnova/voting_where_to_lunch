package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {
    void delete(int id) throws NotFoundException;

    Menu save(Menu menu, int restaurantId);

    Menu get(int id) throws NotFoundException;

    Menu getToday(int restaurantId) throws NotFoundException;

    List<Menu> getAll(int restaurantId);

}
