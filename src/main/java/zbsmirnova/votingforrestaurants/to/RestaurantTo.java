package zbsmirnova.votingforrestaurants.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

public class RestaurantTo extends BaseTo implements Serializable{
    //private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    String name;

    public RestaurantTo(){}

    public RestaurantTo(int id, String name){
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
