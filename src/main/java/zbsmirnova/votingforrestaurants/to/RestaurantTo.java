package zbsmirnova.votingforrestaurants.to;

import zbsmirnova.votingforrestaurants.model.Dish;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class RestaurantTo extends BaseTo implements Serializable{
    //private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 100)
    private String address;

    private List<Dish> menu;

    public RestaurantTo(){}

    public RestaurantTo(int id, String name, String address, List<Dish> menu){
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
