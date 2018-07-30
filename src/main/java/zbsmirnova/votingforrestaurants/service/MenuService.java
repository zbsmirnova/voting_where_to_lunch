package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface MenuService {
    void delete(int id);

    Menu save(Menu menu);

    Menu getWithDishes(int id);

    List<Menu> getAll();

    Menu get(int id) throws NotFoundException;
}
