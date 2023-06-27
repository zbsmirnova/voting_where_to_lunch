package zbsmirnova.restaurantvoting.web.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import zbsmirnova.restaurantvoting.AuthorizedUser;
import zbsmirnova.restaurantvoting.service.UserService;
import zbsmirnova.restaurantvoting.to.UserTo;

import static zbsmirnova.restaurantvoting.util.UserUtil.asTo;

@RestController
@RequestMapping(ProfileRestController.PROFILE_URL)
@Slf4j
public class ProfileRestController {

    static final String PROFILE_URL = "/profile";

    private final UserService service;

    @Autowired
    public ProfileRestController(UserService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int userId = authorizedUser.getId();
        log.info("get user {} ", userId);
        return asTo(service.get(userId));
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int userId = authorizedUser.getId();
        log.info("delete user {} ", userId);
        service.delete(userId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int id = authorizedUser.getId();
        log.info("update {} with id={}", userTo, id);
        service.update(userTo, id);
    }
}
