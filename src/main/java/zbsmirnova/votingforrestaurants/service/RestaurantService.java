package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    void delete(int id) throws NotFoundException;

    Restaurant update(Restaurant restaurant);

    Restaurant create(Restaurant restaurant);

    //Restaurant update(RestaurantTo restaurantTo) throws NotFoundException;

    Restaurant get(int id);

    Restaurant getWithMenus(int id);
}
