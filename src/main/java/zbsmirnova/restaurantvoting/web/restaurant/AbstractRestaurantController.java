package zbsmirnova.restaurantvoting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.service.RestaurantService;
import zbsmirnova.restaurantvoting.to.RestaurantTo;

import java.util.List;

import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.createNewFromTo;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    public RestaurantTo get(int restaurantId){
        log.info("get restaurant {} ", restaurantId);
        return asTo(service.getWithTodayMenu(restaurantId));
    }

    public List<Restaurant> getAll(){
        log.info("get all restaurants");
        return service.getAll();
    }

    public List<RestaurantTo> getAllWithTodayMenu(){
        log.info("get all restaurants with today menu");
        return service.getAllWithTodayMenu();
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        service.delete(id);
    }

    public Restaurant create(RestaurantTo restaurantTo){
        log.info("create restaurant {}", restaurantTo);
        checkNew(restaurantTo);
        return service.create(createNewFromTo(restaurantTo));
    }

    public void update(RestaurantTo restaurantTo, int id){
        log.info("update restaurant {}", restaurantTo);
        assureIdConsistent(restaurantTo, id);
        service.update(restaurantTo);
    }
}
