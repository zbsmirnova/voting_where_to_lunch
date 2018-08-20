package zbsmirnova.votingforrestaurants.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zbsmirnova.votingforrestaurants.AuthorizedUser;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.service.UserService;
import zbsmirnova.votingforrestaurants.to.UserTo;

import javax.validation.Valid;
import javax.validation.groups.Default;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(ProfileController.PROFILE_URL)
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    UserService service;

    static final String PROFILE_URL = "/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int userId = authorizedUser.getId();
        log.info("get user {} ", userId);
        return service.get(userId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int userId = authorizedUser.getId();
        log.info("delete user {} ", userId);
        service.delete(userId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(Default.class) @RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int id = authorizedUser.getId();
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo, id);
    }


}
