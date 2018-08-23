package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.repository.RestaurantRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;


@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Cacheable("restaurants")
    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        if(!restaurant.isNew() && get(restaurant.getId()) == null) return null;
        return repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

}
