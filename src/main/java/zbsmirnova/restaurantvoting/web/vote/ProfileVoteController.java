package zbsmirnova.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.restaurantvoting.AuthorizedUser;
import zbsmirnova.restaurantvoting.service.VoteService;
import zbsmirnova.restaurantvoting.to.VoteTo;
import zbsmirnova.restaurantvoting.util.VoteUtil;

import java.net.URI;

import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;
import static zbsmirnova.restaurantvoting.util.VoteUtil.asTo;

@RestController
@Slf4j
public class ProfileVoteController {
    static final String POST_URL = "/profile/restaurants/{restaurantId}/votes";

    static final String GET_URL = "/profile/votes";

    private final VoteService service;

    @Autowired
    public ProfileVoteController(VoteService service) {
        this.service = service;
    }

    @GetMapping(value = GET_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public VoteTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Getting today vote by user {}", authorizedUser.getId());
        return checkNotFoundWithId(asTo(service.getTodayByUserId(authorizedUser.getId())), authorizedUser.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@PathVariable("restaurantId") int restaurantId, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        log.info("Creating vote from user {} for restaurant {}", authorizedUser.getId(), restaurantId);
        VoteTo created = VoteUtil.asTo(service.create(authorizedUser.getId(), restaurantId));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{restaurantId}")
                    .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{voteId}/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int voteId, @PathVariable("restaurantId") int restaurantId) {
        log.info("Updating vote {} for restaurant {}", voteId, restaurantId);
        service.update(voteId, restaurantId);
    }
}
