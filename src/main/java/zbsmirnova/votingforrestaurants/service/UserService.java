package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    void delete(int id) throws NotFoundException;

    User save(User user);

    User get(int id) throws NotFoundException;

    List<User> getAll();

    //User getByEmail(String email) throws NotFoundException;

//    @Query("SELECT u FROM User u LEFT JOIN FETCH u.meals WHERE u.id = ?1")
//    @EntityGraph(value = User.GRAPH_WITH_MEALS)

    User getWithVotes(int id);
}
