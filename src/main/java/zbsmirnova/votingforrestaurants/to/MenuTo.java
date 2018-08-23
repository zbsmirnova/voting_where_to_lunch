package zbsmirnova.votingforrestaurants.to;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class MenuTo extends BaseTo implements Serializable {
    @NotNull
    private LocalDate date;

    @NotNull
    private String restaurantId;

    public MenuTo(){}

    public MenuTo(int id,  LocalDate date, int restaurantId) {
        super(id);
        this.date = date;
        this.restaurantId = String.valueOf(restaurantId);
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = String.valueOf(restaurantId);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
