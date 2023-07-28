package zbsmirnova.restaurantvoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.service.RestaurantService;
import zbsmirnova.restaurantvoting.to.RestaurantTo;

import java.util.List;

import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.createNewFromTo;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantService service;

    public RestaurantTo get(int restaurantId) {
        log.info("Getting restaurant {} ", restaurantId);
        return asTo(service.getWithTodayMenu(restaurantId));
    }

    public List<Restaurant> getAll() {
        log.info("Getting all restaurants");
        return service.getAll();
    }

    public List<RestaurantTo> getAllWithMenu() {
        log.info("Getting all restaurants with menu");
        return service.getAllWithMenu();
    }

    public void delete(int id) {
        log.info("Deleting restaurant {}", id);
        service.delete(id);
    }

    public Restaurant create(RestaurantTo restaurantTo) {
        log.info("Creating restaurant {}", restaurantTo);
        return service.create(createNewFromTo(restaurantTo));
    }

    public void update(RestaurantTo restaurantTo, int id) {
        log.info("Updating restaurant {}", restaurantTo);
        service.update(restaurantTo, id);
    }
}
