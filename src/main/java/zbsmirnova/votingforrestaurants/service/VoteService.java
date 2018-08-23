package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    void delete(int id) throws NotFoundException;

    Vote get(int id) throws NotFoundException;

    Vote getTodayByUserId(int userId) throws NotFoundException;

    Vote save(Vote vote, int userId, int restaurantId);

    List<Vote> getAll();

    List<Vote> getAll(int restaurantId, LocalDate date);

    List<Vote> getTodayVotes();
}
