package zbsmirnova.restaurantvoting.service;

import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote get(int id) throws NotFoundException;

    Vote getTodayByUserId(int userId) throws NotFoundException;

    List<Vote> getAll();

    List<Vote> getAll(int restaurantId, LocalDate date);

    List<Vote> getTodayVotes();

    void delete(int id) throws NotFoundException;

    Vote create(int userId, int restaurantId);

    void update(int voteId, int restaurantId);
}