package zbsmirnova.votingforrestaurants.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zbsmirnova.votingforrestaurants.service.DishService;
import zbsmirnova.votingforrestaurants.to.DishTo;

import java.util.List;

import static zbsmirnova.votingforrestaurants.util.DishUtil.asTo;


@RestController
@RequestMapping(ProfileDishController.URL)
public class ProfileDishController {
    private static final Logger log = LoggerFactory.getLogger(ProfileDishController.class);

    static final String URL = "/profile/restaurants/{restaurantId}/menus/{menuId}/dishes";

    private final DishService service;

    @Autowired
    public ProfileDishController(DishService service) {
        this.service = service;
    }

    @GetMapping(value = "/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DishTo get(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId,
                    @PathVariable("dishId") int dishId){
        log.info("get dish {} for menu {} for restaurant {} ", dishId, menuId, restaurantId);
        return asTo(service.get(dishId));}

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DishTo> getAll(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId){
        log.info("get all dishes {} for menu {} for restaurant {} ", menuId, restaurantId);
        return asTo(service.getAll(menuId));}
}
