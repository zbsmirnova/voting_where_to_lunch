package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.repository.VoteRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFound;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantService restaurantService;

    private final UserService userService;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, RestaurantService restaurantService, UserService userService) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @Override
    public void delete(int id) throws NotFoundException{
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
    }

    @Override
    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(voteRepository.findById(id).orElse(null), id);
    }

    @Override
    public Vote getTodayByUserId(int userId) throws NotFoundException {
        return voteRepository.findByUserIdAndVoteDate(userId, LocalDate.now()).orElse(null);
    }

    @Transactional
    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        if(!vote.isNew() && get(vote.getId()) == null) return null;
        vote.setRestaurant(restaurantService.get(restaurantId));
        vote.setUser(userService.get(userId));
        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    @Override
    public List<Vote> getAll(int restaurantId, LocalDate date) {
        return voteRepository.getAllByRestaurantIdAndVoteDate(restaurantId, date);
    }

    @Override
    public List<Vote> getTodayVotes() {
        return voteRepository.getAllByVoteDate(LocalDate.now());
    }
}
