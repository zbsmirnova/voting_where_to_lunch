package zbsmirnova.votingforrestaurants.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.service.DishService;

import java.util.List;


@RestController
@RequestMapping(ProfileDishController.URL)
public class ProfileDishController {
    private static final Logger log = LoggerFactory.getLogger(ProfileDishController.class);

    @Autowired
    DishService service;

    static final String URL = "/profile/restaurants/{restaurantId}/menus/{menuId}/dishes";

    @GetMapping(value = "/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId,
                    @PathVariable("dishId") int dishId){
        log.info("get dish {} for menu {} for restaurant {} ", dishId, menuId, restaurantId);
        return service.get(dishId);}

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllForMenu(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId){
        log.info("get all dishes {} for menu {} for restaurant {} ", menuId, restaurantId);
        return service.getAll(menuId);}
}
