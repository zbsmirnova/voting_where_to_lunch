package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    void delete(int id) throws NotFoundException;

    Dish save(Dish dish, int menuId);

    Dish get (int id) throws NotFoundException;

    List<Dish> getAll(int menuId);

}
