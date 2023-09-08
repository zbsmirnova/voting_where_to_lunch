package zbsmirnova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.restaurantvoting.model.Dish;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.repository.DishRepository;
import zbsmirnova.restaurantvoting.repository.RestaurantRepository;
import zbsmirnova.restaurantvoting.to.RestaurantTo;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static zbsmirnova.restaurantvoting.util.RestaurantUtil.asTo;
import static zbsmirnova.restaurantvoting.util.RestaurantUtil.updateFromTo;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.*;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public Restaurant get(int restaurantId) {
        return checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant " + restaurantId + " not found")), restaurantId);
    }

    @Cacheable("restaurantWithTodayMenu")
    @Transactional
    public Restaurant getWithTodayMenu(int restaurantId) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant " + restaurantId + " not found")), restaurantId);
        List<Dish> todayMenu = dishRepository.getTodayMenu(restaurantId, LocalDate.now());
        Objects.requireNonNull(restaurant).setDishes(todayMenu);
        return restaurant;
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Cacheable("allRestaurantsWithTodayMenu")
    public List<RestaurantTo> getAllWithMenu() {
        return asTo(restaurantRepository.getAllWithMenu());
    }

    @Caching(evict = {
            @CacheEvict(value = "restaurantWithTodayMenu", key = "#id"),
            @CacheEvict(value = "allRestaurantsWithTodayMenu", allEntries = true),
            @CacheEvict(value = "restaurants", allEntries = true)
    })
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "restaurantWithTodayMenu", key = "#restaurant.id"),
            @CacheEvict(value = "allRestaurantsWithTodayMenu", allEntries = true),
            @CacheEvict(value = "restaurants", allEntries = true)
    })
    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "restaurantWithTodayMenu", key = "#restaurantTo.id"),
            @CacheEvict(value = "allRestaurantsWithTodayMenu", allEntries = true),
            @CacheEvict(value = "restaurants", allEntries = true)
    })
    public void update(RestaurantTo restaurantTo, int id) {
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = get(restaurantTo.getId());
        restaurantRepository.save(updateFromTo(restaurant, restaurantTo));
    }
}
