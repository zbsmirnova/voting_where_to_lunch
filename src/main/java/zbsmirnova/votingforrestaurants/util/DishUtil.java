package zbsmirnova.votingforrestaurants.util;

import zbsmirnova.votingforrestaurants.model.Dish;
import zbsmirnova.votingforrestaurants.to.DishTo;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DishUtil {
    public static Dish createNewFromTo(DishTo newDish) {
        return new Dish(newDish.getPrice(), newDish.getName());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        return dish;
    }

    public static DishTo asTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice(), dish.getMenu().getId());
    }
    public static List<DishTo> asTo(List<Dish> dishes){
        return dishes.stream().map(DishUtil::asTo).collect(toList());
    }

}
