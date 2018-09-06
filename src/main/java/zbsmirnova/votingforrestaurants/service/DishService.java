package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    Dish get (int dishId, int restaurantId) throws NotFoundException;

    List<Dish> getAll(int restaurantId);

    List<Dish> getAllToday(int restaurantId);

    void delete(int dishId, int restaurantId) throws NotFoundException;

    Dish save(Dish dish, int restaurantId);

}
