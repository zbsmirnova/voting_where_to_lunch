package zbsmirnova.votingforrestaurants.testData;

import zbsmirnova.votingforrestaurants.model.Menu;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.*;
import static zbsmirnova.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final int KFC_EXPIRED_MENU_ID = START_SEQ + 4;
    public static final int MCDONALDS_EXPIRED_MENU_ID = START_SEQ + 5;
    public static final int KETCHUP_EXPIRED_MENU_ID = START_SEQ + 6;
    public static final int BUSHE_EXPIRED_MENU_ID = START_SEQ + 7;

    public static final int KFC_ACTUAL_MENU_ID = START_SEQ + 8;
    public static final int MCDONALDS_ACTUAL_MENU_ID = START_SEQ + 9;
    public static final int KETCHUP_ACTUAL_MENU_ID = START_SEQ + 10;
    public static final int BUSHE_ACTUAL_MENU_ID = START_SEQ + 11;


    public static final Menu KFC_EXPIRED_MENU = new Menu(KFC_EXPIRED_MENU_ID, KFC, LocalDate.parse("2018-07-25"));
    public static final Menu MCDONALDS_EXPIRED_MENU = new Menu(MCDONALDS_EXPIRED_MENU_ID, MCDONALDS, LocalDate.parse("2018-07-25"));
    public static final Menu KETCHUP_EXPIRED_MENU = new Menu(KETCHUP_EXPIRED_MENU_ID, KETCHUP, LocalDate.parse("2018-07-25"));
    public static final Menu BUSHE_EXPIRED_MENU = new Menu(BUSHE_EXPIRED_MENU_ID, BUSHE, LocalDate.parse("2018-07-25"));

    public static final Menu KFC_ACTUAL_MENU = new Menu(KFC_ACTUAL_MENU_ID, KFC, LocalDate.now());
    public static final Menu MCDONALDS_ACTUAL_MENU = new Menu(MCDONALDS_ACTUAL_MENU_ID, MCDONALDS, LocalDate.now());
    public static final Menu KETCHUP_ACTUAL_MENU = new Menu(KETCHUP_ACTUAL_MENU_ID, KETCHUP, LocalDate.now());
    public static final Menu BUSHE_ACTUAL_MENU = new Menu(BUSHE_ACTUAL_MENU_ID, BUSHE, LocalDate.now());

    public static final List<Menu> ALL_MENUS = Arrays.asList(KFC_EXPIRED_MENU, MCDONALDS_EXPIRED_MENU, KETCHUP_EXPIRED_MENU, BUSHE_EXPIRED_MENU,
                                                        KFC_ACTUAL_MENU, MCDONALDS_ACTUAL_MENU, KETCHUP_ACTUAL_MENU, BUSHE_ACTUAL_MENU);


    public static Menu getCreatedMenu() {
        return new Menu(KETCHUP, LocalDate.parse("2018-07-26"));
    }

    public static Menu getUpdatedMenu() {
        return new Menu(KETCHUP_EXPIRED_MENU_ID, KETCHUP, LocalDate.parse("2018-07-26"));
    }


    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant", "dishes");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "dishes").isEqualTo(expected);
    }

        }
