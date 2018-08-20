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

import java.util.List;

@RestController
@RequestMapping(UserRestaurantController.URL)
public class UserRestaurantController {
    private static final Logger log = LoggerFactory.getLogger(AdminRestaurantController.class);

    @Autowired
    RestaurantService service;

    static final String URL = "/profile/restaurants";

    @GetMapping
    public List<Restaurant> getAll(){
        log.info("get all restaurants");
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id){
        log.info("get restaurant {} ", id);
        return service.get(id);}
}
