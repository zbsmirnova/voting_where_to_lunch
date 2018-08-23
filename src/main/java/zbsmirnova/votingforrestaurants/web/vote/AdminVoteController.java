package zbsmirnova.votingforrestaurants.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zbsmirnova.votingforrestaurants.service.VoteService;
import zbsmirnova.votingforrestaurants.to.VoteTo;


import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.VoteUtil.asTo;

@RestController
@RequestMapping(AdminVoteController.URL)
public class AdminVoteController {
    private static final Logger log = LoggerFactory.getLogger(AdminVoteController.class);

    static final String URL = "/admin/votes";

    private final VoteService service;

    @Autowired
    public AdminVoteController(VoteService service) {
        this.service = service;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete vote {}", id);
        service.delete(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo get(@PathVariable("id") int id) {
        log.info("get vote id={}", id);
        return asTo(service.get(id));
    }

    @GetMapping
    public List<VoteTo> getAll(){
        log.info("get all votes");
        return asTo(service.getAll());
    }

    @GetMapping(value = "/{restaurantId}")
    public List<VoteTo> getAllForDateByRestaurantId(@PathVariable("restaurantId") int restaurantId,
                                                    @RequestParam(value = "date", required = false) LocalDate date){
        log.info("get all votes for restaurant {} on date {}", restaurantId, date);
        return asTo(service.getAll(restaurantId, date));
    }

    @GetMapping(value = "/todayVotes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> getTodayVotes() {
        log.info("get today votes");
        return asTo(service.getTodayVotes());
    }
}
