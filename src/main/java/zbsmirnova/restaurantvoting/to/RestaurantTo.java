package zbsmirnova.restaurantvoting.to;

import zbsmirnova.restaurantvoting.model.Dish;
import zbsmirnova.restaurantvoting.model.Restaurant;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class RestaurantTo extends BaseTo implements Serializable {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 100)
    private String address;

    private List<Dish> menu;

    public RestaurantTo() {
    }

    public RestaurantTo(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public RestaurantTo(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getDishes());
    }

    public RestaurantTo(int id, String name, String address, List<Dish> menu) {
        super(id);
        this.name = name;
        this.address = address;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id = " + id +
                ", name = " + name + '\'' +
                ", address = " + address + '\'' +
                ", menu = " + menu +
                '}';
    }
}