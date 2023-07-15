package zbsmirnova.restaurantvoting.web.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.restaurantvoting.model.User;
import zbsmirnova.restaurantvoting.service.UserService;
import zbsmirnova.restaurantvoting.to.UserTo;
import zbsmirnova.restaurantvoting.util.UserUtil;

import java.net.URI;
import java.util.List;

import static zbsmirnova.restaurantvoting.util.UserUtil.asTo;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static zbsmirnova.restaurantvoting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(AdminController.URL)
@Slf4j
public class AdminController {

    static final String URL = "/api/admin/users";

    private final UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get(@PathVariable("id") int id) {
        log.info("get user {} ", id);
        return asTo(service.get(id));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserTo> getAll() {
        log.info("get all users");
        return asTo(service.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete user {}", id);
        service.delete(id);
    }

    //creates only user(ROLE.USER)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserTo userTo) {
        checkNew(userTo);

        User user = UserUtil.createNewFromTo(userTo);

        User created = service.save(user);

        log.info("create user {}", user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable("id") int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo, id);
    }
}
