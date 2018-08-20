package zbsmirnova.votingforrestaurants.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.votingforrestaurants.model.User;
import zbsmirnova.votingforrestaurants.service.UserService;


import javax.validation.groups.Default;
import java.net.URI;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNew;


@RestController
@RequestMapping(RootController.REST_URL)
public class RootController {
    static final String REST_URL = "/rest";

    @Autowired
    UserService service;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerNewUserAccount(@Validated(Default.class)@RequestBody User user) {
        checkNew(user);
        logger.info("creating new user account {}", user);
        User created = service.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}
