package zbsmirnova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.restaurantvoting.model.Dish;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.repository.DishRepository;
import zbsmirnova.restaurantvoting.repository.RestaurantRepository;
import zbsmirnova.restaurantvoting.to.RestaurantTo;
import zbsmirnova.restaurantvoting.util.RestaurantUtil;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.updateFromTo;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;


@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public Restaurant get(int restaurantId){
        return checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
    }

    @Transactional
    @Override
    public Restaurant getWithTodayMenu(int restaurantId) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
        List<Dish> todayMenu = dishRepository.getTodayMenu(restaurantId, LocalDate.now());
        restaurant.setDishes(todayMenu);
        return restaurant;
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Cacheable("restaurantsWithTodayMenu")
    @Override
    public List<RestaurantTo> getAllWithTodayMenu() {
        List<Dish> dishes = dishRepository.getTodayWithRest();
        List<Restaurant> restaurantsWithTodayDishes = new ArrayList<>();
        for (Dish dish: dishes) {
            Restaurant restaurant =  dish.getRestaurant();
            List<Dish> todayDishes;
            if(restaurantsWithTodayDishes.contains(restaurant)) {
                List<Dish> dishes1 = new ArrayList<>(restaurant.getDishes());
                dishes1.add(dish);
                restaurant.setDishes(dishes1);
            }
            else{
                todayDishes = Arrays.asList(dish);
                restaurant.setDishes(todayDishes);
                restaurantsWithTodayDishes.add(restaurant);
            }
        }
        return asTo(restaurantsWithTodayDishes);
//
//        taking a list of all restaurants from db? in stream taking today menu for each of them
//         and filtering to the result list those ones, which have not empty today menu

//        List<Restaurant> allRestaurants = getAll();
//         return allRestaurants.stream()
//                .peek(restaurant-> restaurant.setDishes(dishRepository.getTodayMenu(restaurant.getId(), LocalDate.now())))
//                .filter(restaurant -> !restaurant.getDishes().isEmpty())
//                .collect(Collectors.toList());
//
    }

    @CacheEvict(value = "restaurantsWithTodayMenu", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }


    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurantsWithTodayMenu", allEntries = true)
    @Override
    public void update(RestaurantTo restaurantTo) {
        Restaurant restaurant = get(restaurantTo.getId());
        restaurantRepository.save(updateFromTo(restaurant, restaurantTo));
    }
}
