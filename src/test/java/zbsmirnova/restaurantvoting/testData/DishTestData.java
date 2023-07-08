package zbsmirnova.restaurantvoting.testData;

import org.springframework.test.web.servlet.ResultMatcher;
import zbsmirnova.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static zbsmirnova.restaurantvoting.TestUtil.START_SEQ_TEST;
import static zbsmirnova.restaurantvoting.testData.RestaurantTestData.*;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeIgnoreProps;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeValue;

public class DishTestData {
    public static final int CHICKEN_ID = START_SEQ_TEST + 4;
    public static final int FRIES_ID = START_SEQ_TEST + 5;
    public static final int COLA_ID = START_SEQ_TEST + 6;
    public static final int KETCHUPBURGER_ID = START_SEQ_TEST + 10;
    public static final int SALAD_ID = START_SEQ_TEST + 11;
    public static final int WATER_ID = START_SEQ_TEST + 12;
    public static final int CAKE_ID = START_SEQ_TEST + 13;
    public static final int BREAD_ID = START_SEQ_TEST + 14;
    public static final int COFFEE_ID = START_SEQ_TEST + 15;

    public static final int CHICKEN_SPECIAL_ID = START_SEQ_TEST + 16;
    public static final int KETCHUPBURGER_SPECIAL_ID = START_SEQ_TEST + 18;
    public static final int CAKE_SPECIAL_ID = START_SEQ_TEST + 19;

    public static final Dish CHICKEN = new Dish(CHICKEN_ID, 12000, "chicken", KFC, LocalDate.parse("2018-07-25"));
    public static final Dish FRIES = new Dish(FRIES_ID, 12500, "fries", KFC, LocalDate.parse("2018-07-25"));
    public static final Dish COLA = new Dish(COLA_ID, 8000, "cola", KFC, LocalDate.parse("2018-07-25"));
    public static final Dish KETCHUPBURGER = new Dish(KETCHUPBURGER_ID, 25000, "ketchup_burger", KETCHUP, LocalDate.parse("2018-07-27"));
    public static final Dish SALAD = new Dish(SALAD_ID, 20000, "salad", KETCHUP, LocalDate.parse("2018-07-27"));
    public static final Dish WATER = new Dish(WATER_ID, 7000, "water", KETCHUP, LocalDate.parse("2018-07-27"));
    public static final Dish CAKE = new Dish(CAKE_ID, 18080, "cake", BUSHE, LocalDate.parse("2018-07-27"));
    public static final Dish BREAD = new Dish(BREAD_ID, 9080, "bread", BUSHE, LocalDate.parse("2018-07-27"));
    public static final Dish COFFEE = new Dish(COFFEE_ID, 10080, "coffee", BUSHE, LocalDate.parse("2018-07-27"));
    public static final Dish CHICKEN_SPECIAL = new Dish(CHICKEN_SPECIAL_ID, 12000, "chicken_special", KFC, LocalDate.now());
    public static final Dish KETCHUPBURGER_SPECIAL = new Dish(KETCHUPBURGER_SPECIAL_ID, 25000, "ketchup_burger_special", KETCHUP, LocalDate.now());
    public static final Dish CAKE_SPECIAL = new Dish(CAKE_SPECIAL_ID, 18080, "cake_special", BUSHE, LocalDate.now());

    public static Dish getCreatedDish() {
        return new Dish(10000, "created dish", KFC, LocalDate.parse("2018-07-29"));
    }

    public static Dish getUpdatedDish() {
        return new Dish(CHICKEN_ID, 20000, "updated chicken", KFC, LocalDate.parse("2018-07-29"));
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Dish expected) {
        return content().json(writeIgnoreProps(expected, "restaurant"));
    }

    public static ResultMatcher contentJson(Dish ... expected) {
        return content().json(writeValue(Arrays.asList(expected)));
    }
}
