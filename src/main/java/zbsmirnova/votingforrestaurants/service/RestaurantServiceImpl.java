package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.repository.RestaurantRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    RestaurantRepository repository;

    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(int id) throws NotFoundException {
        //checkNotFoundWithId
        repository.delete(id);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        //checkNotFoundWithId(repository.save(restaurant))
        return repository.save(restaurant);
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        //checkNotFoundWithId
        return repository.getOne(id);
    }

    @Override
    public Restaurant getWithMenus(int id) {
        //checkNotFoundWithId
        return repository.getWithMenus(id);
    }
}
