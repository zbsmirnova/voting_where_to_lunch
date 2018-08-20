package zbsmirnova.votingforrestaurants.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.service.MenuService;

@RestController
@RequestMapping(ProfileMenuController.URL)
public class ProfileMenuController {
    private static final Logger log = LoggerFactory.getLogger(ProfileMenuController.class);

    @Autowired
    MenuService service;

    static final String URL = "/profile/restaurants/{restaurantId}/menus";

    @GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getTodayMenuWithDishes(@RequestParam("restaurantId") int restaurantId) {
        log.info("get today menu with dishes for restaurant id {}", restaurantId);
        return service.getTodayWithDishes(restaurantId);
    }
}
