package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    void delete(int id) throws NotFoundException;

    Restaurant save(Restaurant restaurant);

    Restaurant get(int id) throws NotFoundException ;

    Restaurant getWithMenus(int id) throws NotFoundException ;
}
