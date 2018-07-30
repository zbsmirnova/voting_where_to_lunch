package zbsmirnova.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.repository.VoteRepository;
import zbsmirnova.votingforrestaurants.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    VoteRepository repository;

    @Override
    public void delete(int id) throws NotFoundException{
        repository.delete(id);
    }

    @Override
    public Vote save(Vote vote) {
        return repository.save(vote);
    }

//    @Override
//    public List<Vote> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
//        return repository.getBetween(startDate, endDate);
//    }
}
