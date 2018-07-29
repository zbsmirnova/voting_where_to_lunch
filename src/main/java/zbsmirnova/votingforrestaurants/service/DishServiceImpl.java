package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.repository.DishRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

@Service
public class DishServiceImpl implements DishService{
    @Autowired
    DishRepository repository;

    @Override
    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    @Override
    public Dish save(Dish dish) {
        return repository.save(dish);
    }

    @Override
    public List<Dish> getAll() {
        return repository.findAll();
    }
}
