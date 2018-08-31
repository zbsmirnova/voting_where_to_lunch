package zbsmirnova.votingforrestaurants.testData;

import org.springframework.test.web.servlet.ResultMatcher;
import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.to.DishTo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static zbsmirnova.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;
import static zbsmirnova.votingforrestaurants.testData.MenuTestData.*;
import static zbsmirnova.votingforrestaurants.testData.RestaurantTestData.KFC;
import static zbsmirnova.votingforrestaurants.web.json.JsonUtil.writeValue;

public class DishTestData {
    public static final int CHICKEN_ID = START_SEQ + 12;
    public static final int FRIES_ID = START_SEQ + 13;
    public static final int COLA_ID = START_SEQ + 14;
    public static final int CHEESBURGER_ID = START_SEQ + 15;
    public static final int HAMBURGER_ID = START_SEQ + 16;
    public static final int FISHBURGER_ID = START_SEQ + 17;
    public static final int KETCHUPBURGER_ID = START_SEQ + 18;
    public static final int SALAD_ID = START_SEQ + 19;
    public static final int WATER_ID = START_SEQ + 20;
    public static final int CAKE_ID = START_SEQ + 21;
    public static final int BREAD_ID = START_SEQ + 22;
    public static final int COFFEE_ID = START_SEQ + 23;

    public static final int CHICKEN_SPECIAL_ID = START_SEQ + 24;
    public static final int CHEESBURGER_SPECIAL_ID = START_SEQ + 25;
    public static final int KETCHUPBURGER_SPECIAL_ID = START_SEQ + 26;
    public static final int CAKE_SPECIAL_ID = START_SEQ + 27;

    public static final Dish CHICKEN = new Dish(CHICKEN_ID, 12000, "chicken", KFC_EXPIRED_MENU);
    public static final Dish FRIES = new Dish(FRIES_ID, 12500, "fries", KFC_EXPIRED_MENU);
    public static final Dish COLA = new Dish(COLA_ID, 8000, "cola", KFC_EXPIRED_MENU);
    public static final Dish CHEESBURGER = new Dish(CHEESBURGER_ID, 15020, "cheesburger", MCDONALDS_EXPIRED_MENU);
    public static final Dish HAMBURGER = new Dish(HAMBURGER_ID, 12500, "hamburger", MCDONALDS_EXPIRED_MENU);
    public static final Dish FISHBURGER = new Dish(FISHBURGER_ID, 9000, "fishburger", MCDONALDS_EXPIRED_MENU);
    public static final Dish KETCHUPBURGER = new Dish(KETCHUPBURGER_ID, 25000, "ketchup_burger", KETCHUP_EXPIRED_MENU);
    public static final Dish SALAD = new Dish(SALAD_ID, 20000, "salad", KETCHUP_EXPIRED_MENU);
    public static final Dish WATER = new Dish(WATER_ID, 7000, "water", KETCHUP_EXPIRED_MENU);
    public static final Dish CAKE = new Dish(CAKE_ID, 18080, "cake", BUSHE_EXPIRED_MENU);
    public static final Dish BREAD = new Dish(BREAD_ID, 9080, "bread", BUSHE_EXPIRED_MENU);
    public static final Dish COFFEE = new Dish(COFFEE_ID, 10080, "coffee", BUSHE_EXPIRED_MENU);

    public static final Dish CHICKEN_SPECIAL = new Dish(CHICKEN_SPECIAL_ID, 12000, "chicken_special", KFC_ACTUAL_MENU);
    public static final Dish CHEESBURGER_SPECIAL = new Dish(CHEESBURGER_SPECIAL_ID, 15020, "cheesburger_special", MCDONALDS_ACTUAL_MENU);
    public static final Dish KETCHUPBURGER_SPECIAL = new Dish(KETCHUPBURGER_SPECIAL_ID, 25000, "ketchup_burger_special", KETCHUP_ACTUAL_MENU);
    public static final Dish CAKE_SPECIAL = new Dish(CAKE_SPECIAL_ID, 18080, "cake_special", BUSHE_ACTUAL_MENU);

    public static final List<Dish> ALL_DISHES = Arrays.asList(CHICKEN, FRIES, COLA, CHEESBURGER, HAMBURGER, FISHBURGER,
                                                KETCHUPBURGER, SALAD, WATER, CAKE, BREAD, COFFEE,
                                                CHICKEN_SPECIAL, CHEESBURGER_SPECIAL, KETCHUPBURGER_SPECIAL, CAKE_SPECIAL);


    public static Dish getCreatedDish() {
        return new Dish(10000, "created dish");
    }

    public static Dish getUpdatedDish() {
        return new Dish(CHICKEN_ID, 20000, "updated chicken", KFC_EXPIRED_MENU);
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(DishTo expected) {
        return content().json(writeValue(expected));
    }

    public static ResultMatcher contentJson(DishTo ... expected) {
        return content().json(writeValue(Arrays.asList(expected)));
    }
}
