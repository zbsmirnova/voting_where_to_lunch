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
import zbsmirnova.votingforrestaurants.to.RestaurantTo;
import zbsmirnova.votingforrestaurants.to.VoteTo;

import javax.validation.groups.Default;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.createNewFromTo;
import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.updateFromTo;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(AdminRestaurantController.URL)
public class AdminRestaurantController {
    private static final Logger log = LoggerFactory.getLogger(AdminRestaurantController.class);

    static final String URL = "/admin/restaurants";

    private final RestaurantService service;

    @Autowired
    public AdminRestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<RestaurantTo> getAll(){
        log.info("get all restaurants");
        return asTo(service.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo get(@PathVariable("id") int id){
        log.info("get restaurant {} ", id);
        return asTo(service.get(id));}

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Validated(Default.class) @RequestBody RestaurantTo restaurantTo) {
        Restaurant restaurant = createNewFromTo(restaurantTo);

        Restaurant created = service.save(restaurant);

        log.info("create restaurant {}", restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(Default.class) @RequestBody RestaurantTo restaurantTo, @PathVariable("id") int id) {
        Restaurant restaurant = service.get(id);
        updateFromTo(restaurant, restaurantTo);
        log.info("update {} with id={}", restaurant, id);
        service.save(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete restaurant {}", id);
        service.delete(id);
    }

//    @GetMapping(value = "/admin/restaurants/{restaurantId}/votes/")
//    public List<VoteTo> getAllVotesForDateByRestaurantId(@PathVariable("restaurantId") int restaurantId,
//                                                    @RequestParam(value = "date", required = false) LocalDate date){
//        log.info("get all votes for restaurant {} on date {}", restaurantId, date);
//        return asTo(service.getAll(restaurantId, date));
//    }

}
