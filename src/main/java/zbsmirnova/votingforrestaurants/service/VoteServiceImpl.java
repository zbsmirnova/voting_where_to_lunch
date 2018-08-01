package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.repository.VoteRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository repository;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    @Override
    public void delete(int id) throws NotFoundException{
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        if(!vote.isNew() && get(vote.getId()) == null) return null;
        vote.setRestaurant(restaurantService.get(restaurantId));
        vote.setUser(userService.get(userId));
        return repository.save(vote);
    }

    @Override
    public List<Vote> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return repository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Vote> getAll(LocalDate date) {
        return repository.getAllByVoteDate(date);
    }

    @Override
    public List<Vote> getAllByUser(int userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public Vote getWithRestaurant(int id) {
        return checkNotFoundWithId(repository.getWithRestaurant(id), id);
    }

}
