package zbsmirnova.restaurantvoting.web.restaurant;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.to.RestaurantTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(AdminRestaurantController.URL)
public class AdminRestaurantController extends AbstractRestaurantController {

    static final String URL = "/api/admin/restaurants";

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/getAllWithMenu")
    public List<RestaurantTo> getAllWithMenu() {
        return super.getAllWithMenu();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {

        Restaurant created = super.create(restaurantTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable("id") int id) {
        super.update(restaurantTo, id);
    }
}
