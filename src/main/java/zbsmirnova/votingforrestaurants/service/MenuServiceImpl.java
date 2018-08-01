package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.repository.MenuRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    MenuRepository repository;

    @Autowired
    RestaurantService restaurantService;

    @Override
    public void delete(int id) throws NotFoundException{
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Menu save(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        if(!menu.isNew() && get(menu.getId()) == null) return null;
        menu.setRestaurant(restaurantService.get(restaurantId));
        return repository.save(menu);
    }

    @Override
    public Menu getWithDishes(int id) throws NotFoundException{
        return checkNotFoundWithId(repository.getWithDishes(id), id);
    }

    @Override
    public List<Menu> getAll() {
        return repository.findAll();
    }

    @Override
    public Menu get(int id) throws NotFoundException{
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return repository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public Menu getByDateWithDishes(int restaurantId, LocalDate date) {
        return checkNotFoundWithId(repository.findByRestaurantIdAndAddDate(restaurantId, date), restaurantId);
    }

    @Override
    public Menu getTodayWithDishes(int restaurantId) {
        return checkNotFoundWithId(repository.findByRestaurantIdAndAddDate(restaurantId, LocalDate.now()), restaurantId);
    }

    @Override
    public List<Menu> getAllByDate(LocalDate date) {
        return repository.findAllByDate(date);
    }
}
