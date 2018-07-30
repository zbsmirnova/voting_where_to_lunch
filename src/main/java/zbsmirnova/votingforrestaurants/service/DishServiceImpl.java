package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.repository.DishRepository;
import zbsmirnova.votingforrestaurants.repository.MenuRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService{

    @Autowired
    DishRepository repository;

    @Autowired
    MenuService menuService;

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Dish save(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        if(!dish.isNew() && get(dish.getId()) == null) return null;
        dish.setMenu(menuService.get(menuId));
        return repository.save(dish);
    }

    @Override
    public Dish get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public List<Dish> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return repository.findAllByMenuIdOrderByName(menuId);
    }
}
