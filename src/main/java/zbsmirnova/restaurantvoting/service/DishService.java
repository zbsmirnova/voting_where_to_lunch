package zbsmirnova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.restaurantvoting.model.Dish;
import zbsmirnova.restaurantvoting.repository.DishRepository;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNew;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository repository;

    private final RestaurantService restaurantService;

    @Autowired
    public DishService(DishRepository repository, RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        this.repository = repository;
    }

    public Dish get(int dishId, int restaurantId) throws NotFoundException {
        return checkNotFoundWithId(repository.getByIdAndRestaurantId(dishId, restaurantId).orElseThrow(()
                -> new NotFoundException("Not found dish id = " + dishId +
                "for restaurant id = " + restaurantId)), dishId);
    }

    public List<Dish> getAll(int restaurantId) {
        return repository.findAllByRestaurantId(restaurantId);
    }

    public List<Dish> getTodayMenu(int restaurantId) {
        checkNotFoundWithId(restaurantService.get(restaurantId), restaurantId);
        return repository.getTodayMenu(restaurantId, LocalDate.now());
    }

    public void delete(int dishId, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(dishId, restaurantId) != 0, dishId);
    }

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        checkNew(dish);
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantService.get(restaurantId));
        return repository.save(dish);
    }

    @Transactional
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        if (get(dish.getId(), restaurantId) == null) return;
        dish.setRestaurant(restaurantService.get(restaurantId));
        repository.save(dish);
    }
}
