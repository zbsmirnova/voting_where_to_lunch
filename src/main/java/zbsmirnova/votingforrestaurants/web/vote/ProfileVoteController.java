package zbsmirnova.votingforrestaurants.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.votingforrestaurants.AuthorizedUser;
import zbsmirnova.votingforrestaurants.model.Vote;
import zbsmirnova.votingforrestaurants.service.VoteService;
import zbsmirnova.votingforrestaurants.to.VoteTo;


import java.net.URI;
import java.time.Clock;
import java.time.LocalTime;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkVotingTime;
import static zbsmirnova.votingforrestaurants.util.VoteUtil.*;

@RestController
public class ProfileVoteController {
    private static final Logger log = LoggerFactory.getLogger(ProfileVoteController.class);

    static final String POST_URL = "/profile/restaurants/{restaurantId}/votes";

    static final String GET_URL = "/profile/votes";

    private Clock clock;

    @Autowired
    private VoteService service;

    @Autowired
    public ProfileVoteController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping(value = GET_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("get today vote by user {}", authorizedUser.getId());
        return checkNotFoundWithId(asTo(service.getTodayByUserId(authorizedUser.getId())), authorizedUser.getId());
    }


    @PostMapping(value = POST_URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdate(@PathVariable("restaurantId") int restaurantId, @AuthenticationPrincipal AuthorizedUser authorizedUser){
        Vote vote = service.getTodayByUserId(authorizedUser.getId());
        //create
        if(vote == null){
            log.info("create vote for restaurant {} by user {}", restaurantId, authorizedUser.getId());
            vote = createNew();
            Vote created = service.save(vote, authorizedUser.getId(), restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{restaurantId}")
                    .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
        }
        //update
        else{
            log.info("update vote {} for restaurant {} by user {}", vote.getId(), restaurantId, authorizedUser.getId());
            LocalTime voteTime = LocalTime.now(clock);
            checkVotingTime(voteTime);
            service.save(vote, authorizedUser.getId(), restaurantId);
            return ResponseEntity.ok().build();
        }
    }
}
