package testData;

import zbsmirnova.votingforrestaurants.model.Restaurant;

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


//    public static void assertMatch(User actual, User expected) {
//        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "meals");
//    }
//
}
