package zbsmirnova.restaurantvoting.util;

import zbsmirnova.restaurantvoting.model.Restaurant;
import zbsmirnova.restaurantvoting.to.RestaurantTo;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class RestaurantUtil {
    public static Restaurant createNewFromTo(RestaurantTo newRestaurant) {
        return new Restaurant(newRestaurant.getName(), newRestaurant.getAddress());
    }

    public static Restaurant createFromToWithId(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName(), restaurantTo.getAddress());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        restaurant.setAddress(restaurantTo.getAddress());
        return restaurant;
    }

    public static RestaurantTo asTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getDishes());
    }
    public static List<RestaurantTo> asTo(List<Restaurant> restaurants){
        return restaurants.stream().map(RestaurantUtil::asTo).collect(toList());
    }

}
