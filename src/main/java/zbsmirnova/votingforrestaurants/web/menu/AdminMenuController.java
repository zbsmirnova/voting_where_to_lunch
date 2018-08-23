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
import zbsmirnova.votingforrestaurants.to.MenuTo;
import zbsmirnova.votingforrestaurants.web.user.AdminController;

import javax.validation.groups.Default;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static zbsmirnova.votingforrestaurants.util.MenuUtil.asTo;
import static zbsmirnova.votingforrestaurants.util.MenuUtil.createNewFromTo;
import static zbsmirnova.votingforrestaurants.util.MenuUtil.updateFromTo;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.assureIdConsistent;
import static zbsmirnova.votingforrestaurants.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(AdminMenuController.URL)
public class AdminMenuController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    static final String URL = "/admin/restaurants/{restaurantId}/menus";

    private final MenuService menuService;

    @Autowired
    public AdminMenuController(MenuService service) {
        this.menuService = service;
    }

    @DeleteMapping(value = "/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId) {
        log.info("delete menu {} for restaurant {}", menuId, restaurantId);
        menuService.delete(menuId);
    }

    @GetMapping
    public List<MenuTo> getAll(@PathVariable("restaurantId") int restaurantId){
        log.info("get all menus for restaurant {}", restaurantId);
        return asTo(menuService.getAll(restaurantId));
    }

    @GetMapping(value = "/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuTo get(@PathVariable("restaurantId") int restaurantId, @PathVariable("menuId") int menuId){
        log.info("get menu {} for restaurant {} ", menuId, restaurantId);
        return asTo(menuService.get(menuId));}

    @GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuTo getToday(@PathVariable("restaurantId") int restaurantId){
        log.info("get today menu for restaurant {} ", restaurantId);
        return asTo(menuService.getToday(restaurantId));}

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createToday(@PathVariable("restaurantId") int restaurantId) {

        Menu newMenu = new Menu();
        newMenu.setDate(LocalDate.now());

        Menu created = menuService.save(newMenu, restaurantId);

        log.info("create menu {} for restaurant {}", newMenu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{menuId}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Menu> create(@Validated(Default.class) @RequestBody MenuTo menuTo, @PathVariable("restaurantId") int restaurantId) {
//
//        menuTo.setRestaurantId(restaurantId);
//        Menu created = service.save(createNewFromTo(menuTo), restaurantId);
//
//        log.info("create menu {} for restaurant {}", created, restaurantId);
//
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{menuId}")
//                .buildAndExpand(created.getId()).toUri();
//
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(Default.class) @RequestBody MenuTo menuTo, @PathVariable("menuId") int menuId,
                       @PathVariable("restaurantId") int restaurantId) {
        Menu menu = menuService.get(menuId);
        updateFromTo(menu, menuTo);
        log.info("update menu {} with id={} for restaurant {}", menu, menuId, restaurantId);
        menuService.save(menu, restaurantId);
    }

}
