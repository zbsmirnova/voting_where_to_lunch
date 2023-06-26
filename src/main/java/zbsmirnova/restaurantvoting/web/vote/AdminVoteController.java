package zbsmirnova.restaurantvoting.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zbsmirnova.restaurantvoting.service.VoteService;
import zbsmirnova.restaurantvoting.to.VoteTo;


import java.util.List;

import static zbsmirnova.restaurantvoting.util.VoteUtil.asTo;

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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo get(@PathVariable("id") int id) {
        log.info("Getting vote id={}", id);
        return asTo(service.get(id));
    }

    @GetMapping
    public List<VoteTo> getAll() {
        log.info("Getting all votes");
        return asTo(service.getAll());
    }

    @GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> getTodayVotes() {
        log.info("Getting today votes");
        return asTo(service.getTodayVotes());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("Deleting vote {}", id);
        service.delete(id);
    }
}
