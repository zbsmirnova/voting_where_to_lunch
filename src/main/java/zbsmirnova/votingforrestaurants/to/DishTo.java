package zbsmirnova.votingforrestaurants.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class DishTo extends BaseTo implements Serializable {
    @NotBlank
    @Size(min = 2, max = 100)
    String name;

    //price in cents, maybe make 2 fields: int dollars + int cents
    @NotNull
    int price;

    public DishTo() {
    }

    public DishTo(Integer id, @NotBlank @Size(min = 2, max = 100) String name, @NotNull int price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
