package zbsmirnova.restaurantvoting.testData;

import org.springframework.test.web.servlet.ResultMatcher;
import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.to.RestaurantTo;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static zbsmirnova.restaurantvoting.model.AbstractBaseEntity.START_SEQ;
import static zbsmirnova.restaurantvoting.testData.DishTestData.*;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeIgnoreProps;
import static zbsmirnova.restaurantvoting.web.json.JsonUtil.writeValue;

public class RestaurantTestData {
    public static final int KFC_ID = START_SEQ;
    public static final int MCDONALDS_ID = START_SEQ + 1;
    public static final int KETCHUP_ID = START_SEQ + 2;
    public static final int BUSHE_ID = START_SEQ + 3;


    public static final Restaurant KFC = new Restaurant(KFC_ID,"kfc", "addressKfc");
    public static final Restaurant MCDONALDS = new Restaurant(MCDONALDS_ID,"mcDonalds", "addressMcDonalds");
    public static final Restaurant KETCHUP = new Restaurant(KETCHUP_ID,"ketchup", "addressKetchup");
    public static final Restaurant BUSHE = new Restaurant(BUSHE_ID,"bushe", "addressBushe");

    public static List<Restaurant> ALL_RESTAURANTS = Arrays.asList(BUSHE, KETCHUP, KFC, MCDONALDS);

    public static List<RestaurantTo> getAllRestaurantToWithTodayMenu(){
        RestaurantTo BusheToWithMenu = new RestaurantTo(BUSHE);
        BusheToWithMenu.setMenu(Arrays.asList(CAKE_SPECIAL));
        RestaurantTo KetchupToWithMenu = new RestaurantTo(KETCHUP);
        KetchupToWithMenu.setMenu(Arrays.asList(KETCHUPBURGER_SPECIAL));
        RestaurantTo KfcToWithMenu  = new RestaurantTo(KFC);
        KfcToWithMenu.setMenu(Arrays.asList(CHICKEN_SPECIAL));

        return Arrays.asList(BusheToWithMenu, KetchupToWithMenu, KfcToWithMenu);

    }

    public static Restaurant getCreatedRestaurant(){
        return new Restaurant("british bakeries", "address british bakeries");
    }

    public static Restaurant getUpdatedRestaurant(){
        return new Restaurant(BUSHE_ID,"bushe updated", "addressBushe updated");
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "votes");
    }

    public static void assertMatch(RestaurantTo actual, RestaurantTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "votes").isEqualTo(expected);
    }

    public static void assertMatch(String actual, String expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "dishes", "votes"));
    }

//    public static ResultMatcher contentJson(List<Restaurant> expected) {
//        return content().json(writeIgnoreProps(expected, "menus", "votes"));
//    }

    public static ResultMatcher contentJson(List<RestaurantTo> expected) {
        return content().json(writeValue(expected));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return content().json(writeIgnoreProps(expected, "dishes", "votes"));
    }

    public static ResultMatcher contentJson(RestaurantTo expected) {
        return content().json(writeIgnoreProps(expected, "dishes", "votes"));
    }
}
