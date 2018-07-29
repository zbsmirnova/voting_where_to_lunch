package zbsmirnova.votingforrestaurants.service;

import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {

    void delete(int id) throws NotFoundException;

    Vote save(Vote vote);

    List<Vote> getBetween(LocalDateTime startDate, LocalDateTime endDate);
}
