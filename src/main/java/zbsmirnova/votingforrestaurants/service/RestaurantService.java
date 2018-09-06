package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    Restaurant get(int id) throws NotFoundException;

    Restaurant getWithTodayMenu(int id) throws NotFoundException;

    List<Restaurant> getAll();

    void delete(int id) throws NotFoundException;

    Restaurant save(Restaurant restaurant);
}
