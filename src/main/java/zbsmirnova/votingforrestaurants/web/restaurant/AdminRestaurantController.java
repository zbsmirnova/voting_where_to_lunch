package zbsmirnova.votingforrestaurants.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.service.RestaurantService;

import javax.validation.groups.Default;
import java.net.URI;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(AdminRestaurantController.URL)
public class AdminRestaurantController {
    private static final Logger log = LoggerFactory.getLogger(AdminRestaurantController.class);

    @Autowired
    RestaurantService service;

    static final String URL = "/admin/restaurants";

    @GetMapping
    public List<Restaurant> getAll(){
        log.info("get all restaurants");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id){
        log.info("get restaurant {} ", id);
        return service.get(id);}

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Validated(Default.class) @RequestBody Restaurant restaurant) {
        checkNew(restaurant);

        Restaurant created = service.save(restaurant);

        log.info("create restaurant {}", restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(Default.class) @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.save(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete restaurant {}", id);
        service.delete(id);
    }

}
