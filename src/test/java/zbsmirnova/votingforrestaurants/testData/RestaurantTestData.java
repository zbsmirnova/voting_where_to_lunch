package zbsmirnova.votingforrestaurants.testData;

import zbsmirnova.votingforrestaurants.model.Restaurant;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static zbsmirnova.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int KFC_ID = START_SEQ;
    public static final int MCDONALDS_ID = START_SEQ + 1;
    public static final int KETCHUP_ID = START_SEQ + 2;
    public static final int BUSHE_ID = START_SEQ + 3;


    public static final Restaurant KFC = new Restaurant(KFC_ID,"kfc");
    public static final Restaurant MCDONALDS = new Restaurant(MCDONALDS_ID,"mcDonalds");
    public static final Restaurant KETCHUP = new Restaurant(KETCHUP_ID,"ketchup");
    public static final Restaurant BUSHE = new Restaurant(BUSHE_ID,"bushe");

    public static List<Restaurant> ALL_RESTAURANTS = Arrays.asList(BUSHE, KETCHUP, KFC, MCDONALDS);

    public static Restaurant getCreatedRestaurant(){
        return new Restaurant("british bakeries");
    }

    public static Restaurant getUpdatedRestaurant(){
        return new Restaurant(BUSHE_ID,"bushe updated");
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menus", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menus", "votes").isEqualTo(expected);
    }
}
