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
import zbsmirnova.votingforrestaurants.web.user.AdminController;


import java.net.URI;
import java.time.Clock;
import java.time.LocalTime;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNotFoundWithUserId;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkVotingTime;
import static zbsmirnova.votingforrestaurants.util.VoteUtil.*;

@RestController
public class ProfileVoteController {
    private static final Logger log = LoggerFactory.getLogger(ProfileVoteController.class);

    static final String URL = "/profile/restaurants/{restaurantId}/votes";

    static final String GET_URL = "/profile/votes/";

    Clock clock;

    private final VoteService service;


    @Autowired
    public ProfileVoteController(VoteService service, Clock clock) {
        this.service = service;
        this.clock = clock;
    }

    @PostMapping(value = URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createOrUpdate(@RequestParam("restaurantId") int restaurantId, @AuthenticationPrincipal AuthorizedUser authorizedUser){
        Vote vote = service.getTodayByUserId(authorizedUser.getId());
        //create
        if(vote == null){
            log.info("create vote for restaurant {} by user {}", restaurantId, authorizedUser.getId());
            vote = createNew();
            Vote created = service.save(vote, authorizedUser.getId(), restaurantId);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{menuId}")
                    .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
        }
        //update
        else{
            log.info("update vote {} for restaurant {} by user {}", vote.getId(), restaurantId, authorizedUser.getId());
            checkVotingTime(LocalTime.now(clock));
            service.save(vote, authorizedUser.getId(), restaurantId);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = GET_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser){
        log.info("get today vote by user {}", authorizedUser.getId());
        return asTo(checkNotFoundWithUserId(service.getTodayByUserId(authorizedUser.getId()), authorizedUser.getId()));
    }
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Vote> createWithLocation(@RequestParam("restaurantId") int restaurantId) {
//
//        checkVotingTime();
//
//        Vote created = service.save(createNew(), AuthorizedUser.id(), restaurantId);
//
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(URL + "/{id}")
//                .buildAndExpand(created.getId()).toUri();
//
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }
//
//    @PutMapping(value = "/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void update(@PathVariable("voteId") int voteId,
//                       @RequestParam("restaurantId") int restaurantId) {
//
//        checkVotingTime();
//
//        Vote vote = service.get(voteId);
//
//        service.save(vote, AuthorizedUser.id(), restaurantId);
//    }

}
