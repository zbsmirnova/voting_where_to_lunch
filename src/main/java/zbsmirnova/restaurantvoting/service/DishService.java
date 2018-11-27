package zbsmirnova.restaurantvoting.service;

import zbsmirnova.restaurantvoting.model.Dish;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.util.List;

public interface DishService {

    Dish get (int dishId, int restaurantId) throws NotFoundException;

    List<Dish> getAll(int restaurantId);

    List<Dish> getTodayMenu(int restaurantId);

    void delete(int dishId, int restaurantId) throws NotFoundException;

    Dish create(Dish dish, int restaurantId);

    void update(Dish dish, int restaurantId);

}
