package zbsmirnova.restaurantvoting.service;

import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.to.RestaurantTo;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    Restaurant get(int id) throws NotFoundException;

    Restaurant getWithTodayMenu(int id) throws NotFoundException;

    List<Restaurant> getAll();

    List<RestaurantTo> getAllWithTodayMenu();

    void delete(int id) throws NotFoundException;

    Restaurant create(Restaurant restaurant);

    void update(RestaurantTo restaurantTo);
}
