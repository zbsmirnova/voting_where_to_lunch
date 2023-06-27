package zbsmirnova.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.restaurantvoting.AuthorizedUser;
import zbsmirnova.restaurantvoting.model.Vote;
import zbsmirnova.restaurantvoting.service.VoteService;
import zbsmirnova.restaurantvoting.to.VoteTo;

import java.net.URI;
import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;

import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkVotingTime;
import static zbsmirnova.restaurantvoting.util.VoteUtil.asTo;
import static zbsmirnova.restaurantvoting.util.VoteUtil.createNew;

@RestController
@Slf4j
public class ProfileVoteController {
    static final String POST_URL = "/profile/restaurants/{restaurantId}/votes";

    static final String GET_URL = "/profile/votes";

    //TODO: move to service
    private Clock clock = Clock.system(ZoneId.systemDefault());

    private final VoteService service;

    @Autowired
    public ProfileVoteController(VoteService service) {
        //this.clock = clock;
        this.service = service;
    }

    @GetMapping(value = GET_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Getting today vote by user {}", authorizedUser.getId());
        return checkNotFoundWithId(asTo(service.getTodayByUserId(authorizedUser.getId())), authorizedUser.getId());
    }


    @PostMapping(value = POST_URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdate(@PathVariable("restaurantId") int restaurantId, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Vote vote = service.getTodayByUserId(authorizedUser.getId());
        //create
        if (vote == null) {
            log.info("Creating vote for restaurant {} by user {}", restaurantId, authorizedUser.getId());
            vote = createNew();
            Vote created = service.save(vote, authorizedUser.getId(), restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{restaurantId}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        //update
        else {
            log.info("Updating vote {} for restaurant {} by user {}", vote.getId(), restaurantId, authorizedUser.getId());
            LocalTime voteTime = LocalTime.now(clock);
            checkVotingTime(voteTime);
            service.save(vote, authorizedUser.getId(), restaurantId);
            return ResponseEntity.ok().build();
        }
    }
}
