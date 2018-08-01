package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    void delete(int id) throws NotFoundException;

    User save(User user);

    User get(int id) throws NotFoundException;

    List<User> getAll();

    User getWithVotes(int id);
}
