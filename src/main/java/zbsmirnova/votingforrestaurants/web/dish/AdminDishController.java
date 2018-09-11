package zbsmirnova.votingforrestaurants.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.service.DishService;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.net.URI;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;


@RestController
@RequestMapping(AdminDishController.URL)
public class AdminDishController {

    private final Logger log = LoggerFactory.getLogger(AdminDishController.class);


    static final String URL = "/admin/restaurants/{restaurantId}/dishes";

    private final DishService service;

    @Autowired
    public AdminDishController(DishService service) {
        this.service = service;
    }

    @GetMapping(value = "/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("restaurantId") int restaurantId,
                      @PathVariable("dishId") int dishId){
        log.info("get dish {} for restaurant {} ", dishId, restaurantId);
        return service.get(dishId, restaurantId);
        }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@PathVariable("restaurantId") int restaurantId){
        log.info("get all dishes {} for restaurant {} ", restaurantId);
        return service.getAll(restaurantId);
        }

    @GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllToday(@PathVariable("restaurantId") int restaurantId){
        log.info("get all dishes {} for restaurant {} for today", restaurantId);
        return service.getAllToday(restaurantId);
    }

    @DeleteMapping(value = "/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId,
                       @PathVariable("dishId") int dishId) {
        log.info("delete dish{} for menu {} for restaurant {}", dishId, restaurantId);
        service.delete(dishId, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@PathVariable("restaurantId") int restaurantId,
                                       @Validated(Default.class) @RequestBody Dish dish) {

        Dish created = service.save(dish, restaurantId);

        log.info("create dish {} for restaurant {}", dish, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{dishId}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Dish dish, @PathVariable("dishId") int dishId,
                       @PathVariable("restaurantId") int restaurantId) {
        assureIdConsistent(dish, dishId);
        log.info("update dish {} for restaurant {}", dish, restaurantId);
        service.save(dish, restaurantId);
    }
}
