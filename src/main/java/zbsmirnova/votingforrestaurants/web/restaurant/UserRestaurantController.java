package zbsmirnova.votingforrestaurants.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.service.RestaurantService;
import zbsmirnova.votingforrestaurants.to.RestaurantTo;

import java.util.List;

import static zbsmirnova.votingforrestaurants.util.RestaurantUtil.asTo;

@RestController
@RequestMapping(UserRestaurantController.URL)
public class UserRestaurantController {
    private static final Logger log = LoggerFactory.getLogger(AdminRestaurantController.class);

    static final String URL = "/profile/restaurants";

    private final RestaurantService service;

    @Autowired
    public UserRestaurantController(RestaurantService service) {
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
}
