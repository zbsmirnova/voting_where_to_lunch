package zbsmirnova.votingforrestaurants.util;

import zbsmirnova.votingforrestaurants.model.Menu;
import zbsmirnova.votingforrestaurants.to.MenuTo;


import java.util.List;

import static java.util.stream.Collectors.toList;

public class MenuUtil {
    public static MenuTo asTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getDate(), menu.getRestaurant().getId());
    }

    public static List<MenuTo> asTo(List<Menu> menus){
        return menus.stream().map(MenuUtil::asTo).collect(toList());
    }

    public static Menu createNewFromTo(MenuTo newMenu) {
        return new Menu(null, newMenu.getDate());
    }

    public static Menu updateFromTo(Menu menu, MenuTo menuTo) {
        menu.setDate(menuTo.getDate());
        return menu;
    }
}
