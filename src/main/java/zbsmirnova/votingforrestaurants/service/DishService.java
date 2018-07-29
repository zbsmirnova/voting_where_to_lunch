package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    void delete(int id) throws NotFoundException;

//    void delete(int id, int menuId) throws NotFoundException;

    Dish save(Dish dish);

    List<Dish> getAll();

}
