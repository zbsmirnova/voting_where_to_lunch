package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.repository.DishRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService{

    private final DishRepository repository;

    private final RestaurantService restaurantService;

    @Autowired
    public DishServiceImpl(DishRepository repository, RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        this.repository = repository;
    }

    @Override
    public Dish get(int dishId, int restaurantId) throws NotFoundException  {
        Dish dish = checkNotFoundWithId(repository.findById(dishId).orElse(null), dishId);
        if(dish == null) return null;
        if(dish.getRestaurant().getId() != restaurantId) return null;
        return dish;
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return repository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Dish> getAllToday(int restaurantId){
        return repository.getTodayMenu(restaurantId, LocalDate.now());
    }

    @Override
    public void delete(int dishId, int restaurantId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(dishId, restaurantId) != 0, dishId);
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        if(!dish.isNew() && get(dish.getId(), restaurantId) == null) return null;
        dish.setRestaurant(restaurantService.get(restaurantId));
        return repository.save(dish);
    }
}
