package zbsmirnova.votingforrestaurants.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.service.UserService;
import zbsmirnova.votingforrestaurants.to.UserTo;
import zbsmirnova.votingforrestaurants.util.UserUtil;


import javax.validation.groups.Default;
import java.net.URI;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.UserUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;



@RestController
@RequestMapping(AdminController.URL)
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    static final String URL = "/admin/users";

    private final UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get(@PathVariable("id") int id) {
        log.info("get user {} ", id);
        return asTo(service.get(id));}


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserTo> getAll() {
        log.info("get all users");
        return asTo(service.getAll());}

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Validated(Default.class) @RequestBody UserTo userTo) {
        User user = UserUtil.createNewFromTo(userTo);

        User created = service.save(user);

        log.info("create user {}", user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(Default.class)@RequestBody UserTo userTo, @PathVariable("id") int id) {

        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id); // ???? OK???
        service.update(userTo, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete user {}", id);
        service.delete(id);
    }

}
