package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.repository.UserRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return repository.getOne(id); //в репо findById
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User getWithVotes(int id) {
        return repository.getWithVotes(id);
    }
}
