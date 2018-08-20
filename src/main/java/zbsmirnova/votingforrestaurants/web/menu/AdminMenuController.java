package zbsmirnova.votingforrestaurants.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.model.Restaurant;
import zbsmirnova.votingforrestaurants.service.MenuService;
import zbsmirnova.votingforrestaurants.web.user.AdminController;

import javax.validation.groups.Default;
import java.net.URI;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(AdminMenuController.URL)
public class AdminMenuController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    MenuService service;

    static final String URL = "/admin/restaurants/{restaurantId}/menus";

    @DeleteMapping(value = "/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId) {
        log.info("delete menu {} for restaurant {}", menuId, restaurantId);
        service.delete(menuId);
    }

    @GetMapping
    public List<Menu> getAll(@PathVariable("restaurantId") int restaurantId){
        log.info("get all menus for restaurant {}", restaurantId);
        return service.getAll(restaurantId);
    }

//    @GetMapping
//    public List<Menu> getAll(){
//        log.info("get all menus ");
//        return service.getAll();
//    }

    @GetMapping(value = "/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId){
        log.info("get menu {} for restaurant {} ", menuId, restaurantId);
        return service.get(menuId);}

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@PathVariable("restaurantId") int restaurantId, @Validated(Default.class) @RequestBody Menu menu) {
        checkNew(menu);

        Menu created = service.save(menu, restaurantId);

        log.info("create menu {} for restaurant {}", menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{menuId}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(Default.class) @RequestBody Menu menu, @PathVariable("menuId") int menuId, @PathVariable("restaurantId") int restaurantId) {
        log.info("update menu {} with id={} for restaurant {}", menu, menuId, restaurantId);
        assureIdConsistent(menu, menuId);
        service.save(menu, restaurantId);
    }

}
