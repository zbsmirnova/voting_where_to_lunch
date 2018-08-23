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
import zbsmirnova.votingforrestaurants.to.DishTo;
import zbsmirnova.votingforrestaurants.web.menu.ProfileMenuController;

import javax.validation.groups.Default;
import java.net.URI;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.DishUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.DishUtil.createNewFromTo;
import static zbsmirnova.votingforrestaurants.util.DishUtil.updateFromTo;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(AdminDishController.URL)
public class AdminDishController {
    private static final Logger log = LoggerFactory.getLogger(ProfileMenuController.class);

    @Autowired
    DishService service;

    static final String URL = "/admin/restaurants/{restaurantId}/menus/{menuId}/dishes";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DishTo> getAll(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId){
        log.info("get all dishes {} for menu {} for restaurant {} ", menuId, restaurantId);
        return asTo(service.getAll(menuId));}

    @GetMapping(value = "/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DishTo get(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId,
                    @PathVariable("dishId") int dishId){
        log.info("get dish {} for menu {} for restaurant {} ", dishId, menuId, restaurantId);
        return asTo(service.get(dishId));}

    @DeleteMapping(value = "/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId,
                       @PathVariable("dishId") int dishId) {
        log.info("delete dish{} for menu {} for restaurant {}", dishId, menuId, restaurantId);
        service.delete(dishId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId,
                                       @Validated(Default.class) @RequestBody DishTo dishTo) {

        Dish dish = createNewFromTo(dishTo);
        Dish created = service.save(dish, menuId);

        log.info("create dish {} for menu {} for restaurant {}", dish, menuId, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{dishId}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(Default.class) @RequestBody DishTo dishTo, @PathVariable("dishId") int dishId,
                       @PathVariable("menuId") int menuId, @PathVariable("restaurantId") int restaurantId) {
        Dish dish = service.get(dishId);
        updateFromTo(dish, dishTo);
        log.info("update dish {} with id={} for menu {} for restaurant {}", dish, dishId, menuId, restaurantId);
        service.save(dish, menuId);
    }
}
