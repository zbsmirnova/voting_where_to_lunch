package zbsmirnova.restaurantvoting.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zbsmirnova.restaurantvoting.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(ProfileRestaurantController.URL)
public class ProfileRestaurantController extends AbstractRestaurantController {
    static final String URL = "/api/profile/restaurants";

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @GetMapping
    public List<RestaurantTo> getAllWithMenu() {
        return super.getAllWithMenu();
    }
}
