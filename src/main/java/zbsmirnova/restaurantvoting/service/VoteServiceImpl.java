package zbsmirnova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.repository.VoteRepository;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFound;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Override
    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(voteRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found vote with id = " + id)), id);
    }

    @Override
    public Vote getTodayByUserId(int userId) throws NotFoundException {
        return voteRepository.findByUserIdAndDate(userId, LocalDate.now()).orElse(null);
    }

    @Override
    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    @Override
    public List<Vote> getAll(int restaurantId, LocalDate date) {
        return voteRepository.getAllByRestaurantIdAndDate(restaurantId, date);
    }


    @Override
    public List<Vote> getTodayVotes() {
        return voteRepository.getAllByDate(LocalDate.now());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
    }

    @Transactional
    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        if (!vote.isNew() && get(vote.getId()) == null) return null;
        vote.setRestaurant(restaurantService.get(restaurantId));
        vote.setUser(userService.get(userId));
        return voteRepository.save(vote);
    }
}
