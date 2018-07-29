package testData;

import zbsmirnova.votingforrestaurants.model.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static zbsmirnova.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int CHICKEN_ID = START_SEQ + 12;
    public static final int CHEESBURGER_ID = START_SEQ + 13;
    public static final int KETCHUPBURGER_ID = START_SEQ + 14;
    public static final int CAKE_ID = START_SEQ + 15;

    public static final int CHICKEN_SPECIAL_ID = START_SEQ + 16;
    public static final int CHEESBURGER_SPECIAL_ID = START_SEQ + 17;
    public static final int KETCHUPBURGER_SPECIAL_ID = START_SEQ + 18;
    public static final int CAKE_SPECIAL_ID = START_SEQ + 19;

    public static final Dish CHICKEN = new Dish(CHICKEN_ID, 12000, "chicken");
    public static final Dish CHEESBURGER = new Dish(CHEESBURGER_ID, 15020, "cheesburger");
    public static final Dish KETCHUPBURGER = new Dish(KETCHUPBURGER_ID, 25000, "ketchup_burger");
    public static final Dish CAKE = new Dish(CAKE_ID, 18080, "cake");

    public static final Dish CHICKEN_SPECIAL = new Dish(CHICKEN_SPECIAL_ID, 12000, "chicken_special");
    public static final Dish CHEESBURGER_SPECIAL = new Dish(CHEESBURGER_SPECIAL_ID, 15020, "cheesburger_special");
    public static final Dish KETCHUPBURGER_SPECIAL = new Dish(KETCHUPBURGER_SPECIAL_ID, 25000, "ketchup_burger_special");
    public static final Dish CAKE_SPECIAL = new Dish(CAKE_SPECIAL_ID, 18080, "cake_special");

    public static final List<Dish> EXPIRED_DISHES = Arrays.asList(CHICKEN, CHEESBURGER, KETCHUPBURGER, CAKE);
    public static final List<Dish> ACTUAL_DISHES = Arrays.asList(CHICKEN_SPECIAL, CHEESBURGER_SPECIAL, KETCHUPBURGER_SPECIAL, CAKE_SPECIAL);
    public static final List<Dish> ALL_DISHES = Arrays.asList(CHICKEN, CHEESBURGER, KETCHUPBURGER, CAKE,
                                                                CHICKEN_SPECIAL, CHEESBURGER_SPECIAL, KETCHUPBURGER_SPECIAL, CAKE_SPECIAL);


}
