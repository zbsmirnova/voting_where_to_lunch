package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.repository.MenuRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    MenuRepository repository;

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public Menu save(Menu menu) {
        return repository.save(menu);
    }

    @Override
    public Menu getWithDishes(int id) {
        return repository.getWithDishes(id);
    }

    @Override
    public List<Menu> getAll() {
        return repository.findAll();
    }

    @Override
    public Menu get(int id) throws NotFoundException{
        return checkNotFoundWithId(repository.getOne(id), id);
    }
}
