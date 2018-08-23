package zbsmirnova.votingforrestaurants.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zbsmirnova.votingforrestaurants.service.MenuService;
import zbsmirnova.votingforrestaurants.to.MenuTo;

import static zbsmirnova.votingforrestaurants.util.MenuUtil.asTo;

@RestController
@RequestMapping(ProfileMenuController.URL)
public class ProfileMenuController {
    private static final Logger log = LoggerFactory.getLogger(ProfileMenuController.class);

    static final String URL = "/profile/restaurants/{restaurantId}/menus";

    private final MenuService service;

    @Autowired
    public ProfileMenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuTo getTodayMenu(@RequestParam("restaurantId") int restaurantId) {
        log.info("get today menu for restaurant id {}", restaurantId);
        return asTo(service.getToday(restaurantId));
    }
}
