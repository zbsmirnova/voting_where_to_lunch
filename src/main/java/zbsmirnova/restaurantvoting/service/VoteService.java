package zbsmirnova.restaurantvoting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.repository.VoteRepository;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static zbsmirnova.restaurantvoting.util.ValidationUtil.*;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantService restaurantService;

    private final UserService userService;

    private Clock clock = Clock.system(ZoneId.systemDefault());

    public VoteService(VoteRepository voteRepository, RestaurantService restaurantService, UserService userService) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(voteRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found vote with id = " + id)), id);
    }

    public Vote getTodayByUserId(int userId) throws NotFoundException {
        return voteRepository.findByUserIdAndDate(userId, LocalDate.now()).orElseThrow(() ->
                new NotFoundException("Not found today vote for userId = " + userId));
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public List<Vote> getAll(int restaurantId, LocalDate date) {
        return voteRepository.getAllByRestaurantIdAndDate(restaurantId, date);
    }


    public List<Vote> getTodayVotes() {
        return voteRepository.getAllByDate(LocalDate.now());
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
    }

    @Transactional
    public Vote create(int userId, int restaurantId) {
        checkVotingTime(clock);
        Vote userVotes = voteRepository.findByUserIdAndDate(userId, LocalDate.now()).orElse(null);
        Assert.isNull(userVotes, "User with id " + userId + "already voted today. Only one vote counted per user");
        Vote vote = new Vote(LocalDate.now());
        if (!vote.isNew() && get(vote.getId()) == null) return null;
        vote.setRestaurant(restaurantService.get(restaurantId));
        vote.setUser(userService.get(userId));
        Assert.notNull(vote, "Vote must not be null");
        return voteRepository.save(vote);
    }

    @Transactional
    public void update(int voteId, int restaurantId) {
        checkVotingTime(clock);
        Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NotFoundException("Vote " + voteId + " not found"));
        Assert.notNull(vote, "Vote must not be null");
        assureIdConsistent(vote, voteId);

        vote.setRestaurant(restaurantService.get(restaurantId));
        checkNotFoundWithId(voteRepository.save(vote), voteId);
    }
}
