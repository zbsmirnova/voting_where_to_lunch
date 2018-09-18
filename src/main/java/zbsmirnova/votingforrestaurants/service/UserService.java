package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.to.UserTo;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface UserService {
    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();

    void delete(int id) throws NotFoundException;

    User save(User user);

    void update(UserTo userTo, int id);
}
